# 一、经典蓝牙

用于数据量较大的传输，如语音，音乐，文件等



权限：

```xml
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<!--位置权限用于搜索附近蓝牙设备-->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
```

可以使用动态权限开启



## 1 开启蓝牙

### 1.1 获取蓝牙设备

```java
BluetoothAdapter mBluetoothAdapter;

//获取BluetoothAdapter
mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
```



### 1.2 打开蓝牙

```java
 //判断是否支持蓝牙，并弹窗要求打开蓝牙
 if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
     //隐式intent同步开启蓝牙，会弹出一个提示框，正在开启蓝牙……
      Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      startActivityForResult(enableBtIntent, 2);
      //异步暴力直接开启，不建议采用
      //mBluetoothAdapter.enable();
 }


 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            //已启用，进行下一步工作
        } else if (requestCode == 2 && resultCode == RESULT_CANCELED) {
            //未启用，退出应用
            Toast.makeText(MainActivity.this, "请启用蓝牙", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
```





## 2 扫描蓝牙

### 2.1 让自己的设备能够被搜索到

不是所有的蓝牙设备都能被找到，如果你的设备是隐藏的，必须要把设备的状态设为可见，才能够被周围设备发现

如果确定自己的蓝夜设备可以被发现，则可以跳过该步骤

```java
//使自己的蓝牙设备可被搜索，会弹出对话框提示,自动开启蓝牙设备
Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//300秒内可见，取值0-3600，超出则为默认120秒，0为始终可见
discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
startActivity(discoverableIntent);
```



### 2.2 适配器

接下来的扫描其它可用设备和获取绑定设备都需要RecyclerView展示，所以先写适配器

子项页面为两个TV（id：item_title 和 item_address），还有一个分割线

```java
public class BTAdapter extends RecyclerView.Adapter<BTAdapter.ViewHoder> {
    private List<BluetoothDevice> mBTList;	//蓝牙的适配器列表，存放搜索到的每个子项
    private Handler mHandler; //用于后面的创建线程，发送消息展示内容

    //构造
    public BTAdapter(List<BluetoothDevice> btList, Handler handler) {
        mBTList = btList;
        mHandler = handler;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bt, parent, false);
        final RecyclerView.ViewHolder holder = new ViewHoder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {	//子项的监听
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();	//获取点击的子项位置
                //这部分为开启客户端线程，后面连接部分再详细讲解
                MainActivity.mConnectThread = new ConnectThread(mBTList.get(position), mHandler);
                MainActivity.mConnectThread.start();
                Toast.makeText(view.getContext(), "正在连接" + mBTList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return (ViewHoder) holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder viewHoder, int i) {
        //设置每个子项的展示内容，
        viewHoder.item_title.setText(mBTList.get(i).getName());	 //蓝牙名称
        viewHoder.item_address.setText(mBTList.get(i).getAddress());  //MAC地址
    }

    @Override
    public int getItemCount() {
        return mBTList.size();
    }

    //内部类，设置每个子项的控件
    static class ViewHoder extends RecyclerView.ViewHolder {
        TextView item_title;
        TextView item_address;
		//构造
        public ViewHoder(@NonNull View view) {
            super(view);
            item_title = view.findViewById(R.id.item_title);
            item_address = view.findViewById(R.id.item_address);
        }
    }
}
```

MA中设置RV的属性

```java
rv_BT = findViewById(R.id.rv_BT);
LinearLayoutManager layoutManager = new LinearLayoutManager(this);
rv_BT.setLayoutManager(layoutManager);
```



### 2.3 获取已绑定的设备

```java
//获取已绑定的设备并显示
public void find_bounded_devices(View view) {
    //获取已绑定的设备，【核心代码】
    Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
    //因为RV接收的为List，将集合元素取出，转为list，再放置到RV中
    ArrayList boundedDeviceList = new ArrayList();
    for (BluetoothDevice bondedDevice : bondedDevices) {
        boundedDeviceList.add(bondedDevice);
    }
    rv_BT.setAdapter(new BTAdapter(boundedDeviceList, handler));	//设置适配器的相关属性
}
```



### 2.4 搜索可用的设备

```java
	//是一个异步函数，12秒
	//查找后需要监听广播，获取查找到的其它设备
    public void find_surrounding_devices(View view) {
        assert (mBluetoothAdapter != null);
        mBluetoothAdapter.startDiscovery();
    }
```

动态注册广播，使用广播接收发现设备的返回结果

```java
	int n = 0;	//记录新的设备数量
	ArrayList newDevices;	//搜索到的可用设备，加载到List列表中
    TextView tv_operation;	//显示当前的操作状态

    tv_operation = findViewById(R.id.tv_operation);
    newDevices = new ArrayList();//搜索可用设备的集合

    //BT的广播接收器，用于返回结果
    private BroadcastReceiver BT_Receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case BluetoothDevice.ACTION_FOUND:  //发现新设备
                    BluetoothDevice newDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (!newDevices.equals(newDevice)) {
                        newDevices.add(newDevice); //去重
                        //因为是异步，所以使用tv显示当前发现的设备数量，等待扫描完毕，再统一显示
                        tv_operation.setText("发现" + (n + 1) + "个新设备");	
                        n++;
                    }
                    break;      //扫描完成
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    Toast.makeText(context, "扫描完毕", Toast.LENGTH_SHORT).show();
                    //扫描结果显示在RV上
                    rv_BT.setAdapter(new BTAdapter(newDevices, handler));
                    n = 0;
                    break;
            }
        }
    };

 	//广播的过滤器
    private IntentFilter BT_IntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        return intentFilter;
    }

 	registerReceiver(BT_Receiver, BT_IntentFilter());   //注册广播

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(BT_Receiver);//注销注册动态广播
    }
```







## 3 蓝牙连接和数据传输

### 3.1 蓝牙的绑定

绑定设备需要API>19，android 4.4 以下不支持！

陌生的设备可以先绑定，然后启用线程进行连接，也可跳过此步，直接启用线程连接

```java
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //SDK API>19
     device.createBond();
}
```



### 3.2 蓝牙连接流程

蓝牙的连接分为客户端和服务器端，服务器端需要先开启 accept() 监听，然后客户端使用和服务器端相同的UUID进行连接，之后才能进行通信。

因为服务器端可能会出现阻塞，客户端会存在多个，发送时也需要持续监听，所以采用三个线程，分别解决上述的问题。

![image-20210710171745935](%E8%93%9D%E7%89%99%E3%80%81WIFI%E9%80%9A%E8%AE%AF%E5%8D%8F%E8%AE%AE.assets/image-20210710171745935.png)

服务器端创建 socket 流程：

1.通过 BluetoothAdapter.listenUsingRfcommWithServiceRecord 创建出一个服务端的BluetoothServerSocket

2.ServerSocket.accept() 监听 客户端的连接

3.处理数据的收发



客户端：

1.通过createRfcommSocketToServiceRecord 创建一个BluetoothSocket

2.连接服务端 connect

3.处理数据



### 3.3 服务器端代码实现

```java
/*
 * 服务器端Socket线程，开启请求，等待客户端连接
 * 连接客户端设备后，开启传输线程
 * */
public class AcceptThread extends Thread {
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothServerSocket mBluetoothServerSocket;     //最终的服务器端Socket，在构造中完成初始化
    private Handler mHandler;
    private ConnectedThread mConnectedThread;//建立连接后的消息发送线程
    private boolean thread_continue = true;

    public AcceptThread(Handler handler) {
        mHandler = handler;
        BluetoothServerSocket tmp = null;   //用于临时存储服务端Socket
        try {
            mBluetoothAdapter.setName("My_BT");//设置蓝牙名称
            //构建服务端的Socket
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("BT_Listener", UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mBluetoothServerSocket = tmp;
        send_msg_to_main(1); //发送消息：服务器端完成构建
    }


    @Override
    public void run() {
        BluetoothSocket bluetoothSocket;
        if (!thread_continue) {
            thread_continue = true;
        }
        while (thread_continue) {
            try {
                //持续监听客户端的连接，当有设备连接时，返回客户端的Socket
                bluetoothSocket = mBluetoothServerSocket.accept();
            } catch (IOException e) {
                send_msg_to_main(3); //发送消息：服务器端出错
                e.printStackTrace();
                break;
            }
            // 如果有设备接受连接
            while (bluetoothSocket != null) {
                //启用新的线程完成数据传输工作
                startConnectedThread(bluetoothSocket);
                send_msg_to_main(4);    //发送消息：有设备接入
                break;
            }
        }
    }

    //发送消息，用于主线程的消息提示
    private void send_msg_to_main(int i) {
        Message message = mHandler.obtainMessage();
        message.what = i;
        mHandler.sendMessage(message);
    }

    private void startConnectedThread(BluetoothSocket bluetoothSocket) {
        //只保持单一线程通信，如果已存在通信的线程，则挂断
        if (mConnectedThread != null) {
            try {
                mConnectedThread.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //构建并启动通信线程
        mConnectedThread = new ConnectedThread(bluetoothSocket, mHandler);
        mConnectedThread.start();
    }

    // 利用通信线程发送数据
    public void sendData(byte[] data) {
        if (mConnectedThread != null) {
            mConnectedThread.write(data);
        }
    }
}
```



### 3.4 客户端代码实现

```java
/*
 * 客户端线程，启动后连接到相同的UUID的服务端Socket
 * 连接成功后，开启新的线程传输数据
 * */
public class ConnectThread extends Thread {
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothSocket mBluetoothSocket;   //客户端的Socket
    private BluetoothDevice mBluetoothDevice;   //客户端的蓝牙设备
    private ConnectedThread mConnectedThread; //通信线程
    private Handler mHandler;


    public ConnectThread(BluetoothDevice bluetoothDevice, Handler handler) {
        mBluetoothDevice = bluetoothDevice;
        mHandler = handler;
        BluetoothSocket tmp = null;  //临时的客户端Socket

        try {
            //建立临时的客户端Socket
            tmp = mBluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        } catch (IOException e) {
            send_msg_to_main(6);//发送消息：客户端出错
            e.printStackTrace();
        }
        mBluetoothSocket = tmp;
        send_msg_to_main(5);//发送消息：客户端开启
    }

    @Override
    public void run() {
        //如果蓝牙设备正在搜寻，需要停止搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        try {
            //尝试和服务器端通过相同的UUID进行连接
            mBluetoothSocket.connect();
            send_msg_to_main(7);//发送消息：连接成功
        } catch (IOException e) {
            send_msg_to_main(8);//发送消息：连接失败
            e.printStackTrace();
            return;
        }
        //启用新的线程完成数据传输工作
        startConnectedThread(mBluetoothSocket);
    }

    //客户端取消连接
    public void cancel() {
        try {
            mBluetoothSocket.close();
            mConnectedThread.cancel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startConnectedThread(BluetoothSocket bluetoothSocket) {
        if (mConnectedThread != null) {
            try {
                mConnectedThread.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mConnectedThread = new ConnectedThread(bluetoothSocket, mHandler);
        mConnectedThread.start();
    }

    // 发送数据
    public void sendData(byte[] data) {
        if (mConnectedThread != null) {
            mConnectedThread.write(data);
        }
    }

    //发送消息，用于主线程的消息提示
    private void send_msg_to_main(int i) {
        Message message = mHandler.obtainMessage();
        message.what = i;
        mHandler.sendMessage(message);
    }
}
```



### 3.5 数据传输代码实现

```java
public class ConnectedThread extends Thread {
    private BluetoothSocket mBluetoothSocket;
    private final InputStream mInputStream;
    private final OutputStream mOutputStream;
    public boolean thread_continue = true;

    private Handler mHandler;
    static String str; //获取发送的消息

    public ConnectedThread(BluetoothSocket bluetoothSocket, Handler handler) {
        mBluetoothSocket = bluetoothSocket;
        mHandler = handler;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        // 使用临时对象获取输入和输出流，获取成功后赋值给成员比变量
        try {
            tmpIn = bluetoothSocket.getInputStream();
            tmpOut = bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            send_msg_to_main(9);//发送消息：数据流出错
            e.printStackTrace();
        }
        mInputStream = tmpIn;
        mOutputStream = tmpOut;
        send_msg_to_main(10);//发送消息：数据流建立成功
    }

    @Override
    public void run() {
        //如果因为上次操作终止了线程，则开启线程
        if (!thread_continue) {
            thread_continue = true;
        }
        // 持续监听InputStream，直到出现异常或断开连接
        while (thread_continue) {
            //连接时循环读取
            byte[] buffer = new byte[1024];  // 用于流的缓冲存储，最长读取1024个字节
            try {
                int length = mInputStream.read(buffer);   //从InputStream读取到的数据长度
                str = new String(buffer, 0, length, "utf-8");
                if (length > 0) {
                    send_msg_to_main(11);//发送消息：通过流获取到的内容
                }
            } catch (IOException e) {
                send_msg_to_main(12);//发送消息：数据流流读取出错
                cancel();
                break;
            }
        }
    }

    //写入数据
    public void write(byte[] buffer) {
        try {
            mOutputStream.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //取消数据传输连接
    public void cancel() {
        try {
            thread_continue = false;
            mBluetoothSocket.close();
            mInputStream.close();
            mOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送消息，用于主线程的消息提示
    private void send_msg_to_main(int i) {
        Message message = mHandler.obtainMessage();
        message.what = i;
        mHandler.sendMessage(message);
    }
}
```





### 3.6 MA中的线程控制

布局文件：

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <EditText
        android:id="@+id/et_msg"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="find_bounded_devices"
            android:text="已绑定设备" />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="find_surrounding_devices"
            android:text="搜索周围设备" />
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="start_listening"
            android:text="开启服务端监听模式" />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="send_msg"
            android:text="发送数据" />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="cancel"
            android:text="取消连接" />
    </LinearLayout>

    <TextView
        android:id="@+id/tv_operation"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="20sp" />

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv_BT"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
</LinearLayout>
```

消息的提示：

```java
//消息输入框的初始化
et_msg = findViewById(R.id.et_msg);

//提示当前操作
public Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                showToast("服务器端开启");
                break;
            case 3:
                showToast("服务器端出错！");
                break;
            case 4:
                showToast("有设备接入");
                break;
            case 5:
                showToast("客户端开启");
                break;
            case 6:
                showToast("客户端出错");
                break;
            case 7:
                showToast("连接成功");
                break;
            case 8:
                showToast("连接失败");
                break;
            case 9:
                showToast("数据流出错");
                break;
            case 10:
                showToast("数据流建立成功");
                break;
            case 11:
                showToast(ConnectedThread.str);
                break;
            case 12:
                showToast("断开连接");
                break;
        }
    }
};

private void showToast(String str) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
}
```



线程的操作：

```java
    //服务器端监听
    public void start_listening(View view) {
        mAcceptThread = new AcceptThread(handler);
        mAcceptThread.start();
        tv_operation.setText("服务器端正在监听");
    }

    //发送消息
    public void send_msg(View view) {
        msg = et_msg.getText().toString();
        if (!TextUtils.isEmpty(msg)) {
            if (mAcceptThread != null) {
                mAcceptThread.sendData(msg.getBytes());
            }
            if (mConnectThread != null) {
                mConnectThread.sendData(msg.getBytes());
            }
        }
    }

    //取消连接
    public void cancel(View view) {
        if (mConnectThread != null) {
            mConnectThread.cancel();
        }
    }
```





# 二、蓝牙BLE

又称为低功耗蓝牙，相比于传统蓝牙，具有传播速度更快，覆盖范围更广，安全性更高，延迟更低，耗电极低等优点。适用于实时性要求较高，数据速率比较低的产品。

传统蓝牙通过Socket方式实现通讯，BLE通过Gatt协议实现，BLE分为三部分：Service，Characteristic，Descriptor。这三部分都用UUID作为唯一标识符。





## 0 预备知识

Android是在4.3后才支持BLE，而且支持BLE的蓝牙手机一般是双模的。双模兼容传统蓝牙，可以和传统蓝牙通信，也可以和BLE通信，常用在手机上。

GATT全称Generic Attribute Profile，中文名叫通用属性协议，它定义了 services 和 characteristic 两种东西来完成低功耗蓝牙设备之间的数据传输。它是建立在通用数据协议Attribute Protocol (ATT) 之上的，ATT把services和characteristic以及相关的数据保存在一张简单的查找表中，该表使用16-bit的id（UUID）作为索引。

![image-20210718091157820](%E8%93%9D%E7%89%99%E3%80%81WIFI%E9%80%9A%E8%AE%AF%E5%8D%8F%E8%AE%AE.assets/image-20210718091157820.png)

### 1、profile

profile可以理解为一种规范，一个标准的通信协议，它存在于从机中。蓝牙组织规定了一些标准的profile，例如 HID OVER GATT ，防丢器 ，心率计等。每个profile中会包含多个service，每个service代表从机的一种能力。

### 2、service

service可以理解为一个服务，在ble从机中，有多个服务，例如电量信息服务、系统信息服务等，每个service中又包含多个characteristic特征值。每个具体的characteristic 特征值才是ble通信的主题。比如当前的电量是80%，所以会通过电量的characteristic特征值存在从机的profile里，这样主机就可以通过这个characteristic来读取80%这个数据

### 3、characteristic

characteristic特征值，ble主从机的通信均是通过characteristic来实现，可以理解为一个标签，通过这个标签可以获取或者写入想要的内容。==Characteristic是手机与BLE终端交换数据的关键==，读取设置数据等操作都是操作Characteristic的相关属性

### 4、UUID

UUID，统一识别码，service和characteristic，都需要一个唯一的UUID来标识

UUID的格式：00001101-0000-1000-8000-00805F9B34FB



**整理一下**，每个从机，都会有一个叫做profile的东西存在，不管是自定义的simpleprofile，还是标准的防丢器profile，都是由一些service组成，每个service又包含了多个characteristic，主机和从机之间的通信，均是通过characteristic来实现。

一般，读，写和通知的UUID 就是 characteristic UUID 。写入数据后，设备会给我们立刻返回一个通知，所以我们需要在通知中获取数据（在这里不是用的读取数据）







### 5、权限：

```xml
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<!--位置权限用于搜索附近蓝牙设备-->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
```

可以使用动态权限开启

```java
// Android 6.0动态请求权限
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.ACCESS_COARSE_LOCATION };
    for (String str : permissions) {
        if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, 111);
            break;
        }
    }
}
```



## 1 开启蓝牙

### 1.1 判断设备是否支持BLE

```java
 if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "设备不支持BLE蓝牙", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "支持BLE蓝牙", Toast.LENGTH_SHORT).show();
        }
```



### 1.2 获取蓝牙设备

跟经典蓝牙的获取方式一样，也可通过蓝牙管理服务获取：

```java
 final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
 BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
```

```java
 BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
```





### 1.3 启动蓝牙

```java
 //判断是否支持蓝牙，并弹窗要求打开蓝牙
 if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
     //隐式intent同步开启蓝牙，会弹出一个提示框，正在开启蓝牙……
      Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      startActivityForResult(enableBtIntent, 2);
      //异步暴力直接开启，不建议采用
      //mBluetoothAdapter.enable();
 }


 	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            //已启用，进行下一步工作
        } else if (requestCode == 2 && resultCode == RESULT_CANCELED) {
            //未启用，退出应用
            Toast.makeText(MainActivity.this, "请启用蓝牙", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
```





## 2 扫描、连接、通讯

### 2.1 适配器

直接将扫描工作放在适配器中完成，需要的时候调用适配器函数即可

子项页面为两个TV（id：name 和 address），还有一个分割线

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="2dp"
    android:background="@drawable/sel_item"
    android:divider="@drawable/divider"
    android:orientation="vertical"
    android:showDividers="end">

    <TextView
        android:id="@+id/name"
        android:textColor="#E91E63"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

    <TextView
        android:id="@+id/address"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintStart_toEndOf="@+id/name" />
</LinearLayout>
```

```java
public class BleDevAdapter extends RecyclerView.Adapter<BleDevAdapter.VH> {
    private static final String TAG = BleDevAdapter.class.getSimpleName();
    private final Listener mListener;
    private final Handler mHandler = new Handler();
    private final List<BleDev> mDevices = new ArrayList<>();
    public boolean isScanning;

    public static class BleDev {
        public BluetoothDevice bluetoothDevice;
        ScanResult scanResult;

        //内部类的构造
        BleDev(BluetoothDevice device, ScanResult result) {
            bluetoothDevice = device;
            scanResult = result;
        }

        //equals和hashCode方法，判断对象是否相等
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BleDev bleDev = (BleDev) o;
            return Objects.equals(bluetoothDevice, bleDev.bluetoothDevice);
        }

        @Override
        public int hashCode() {
            return Objects.hash(bluetoothDevice);
        }
    }

    //内部接口监听器
    public interface Listener {
        //自定义了一个子项点击方法
        void onItemClick(BluetoothDevice dev);
    }

    //构造方法，设置监听器并扫描蓝牙
    BleDevAdapter(Listener listener) {
        mListener = listener;
        scanBle();
    }

    //内部类，ViewHolder，用于设置对应的子项内容
    class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView name;
        final TextView address;

        VH(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position >= 0 && position < mDevices.size()) {
                mListener.onItemClick(mDevices.get(position).bluetoothDevice);
            }
        }
    }

    //清空RV子项并重新扫描
    public void reScan() {
        mDevices.clear();
        notifyDataSetChanged();
        scanBle();
    }

    //扫描BLE蓝牙(不会扫描经典蓝牙)
    private void scanBle() {
        isScanning = true;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        // Android5.0新增的扫描API，扫描返回的结果更友好，比如BLE广播数据以前是byte[] scanRecord，而新API帮我们解析成ScanRecord类
        bluetoothLeScanner.startScan(mScanCallback);
        //延迟三秒执行
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bluetoothLeScanner.stopScan(mScanCallback); //停止扫描
                isScanning = false;
            }
        }, 3000);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dev, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, int position) {
        BleDev bleDev = mDevices.get(position);
        String name = bleDev.bluetoothDevice.getName();
        String address = bleDev.bluetoothDevice.getAddress();
//        holder.name.setText(String.format("%s, %s, Rssi=%s", name, address, dev.scanResult.getRssi()));
//        holder.address.setText(String.format("广播数据{%s}", bleDev.scanResult.getScanRecord()));
        holder.name.setText(name);
        holder.address.setText(address);
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    //扫描蓝牙的接口回调
    private final ScanCallback mScanCallback = new ScanCallback() {// 扫描Callback
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BleDev dev = new BleDev(result.getDevice(), result);
            if (!mDevices.contains(dev)) {
                mDevices.add(dev);
                notifyDataSetChanged();
                Log.e(TAG, "扫描结果: " + result); // result.getScanRecord() 获取BLE广播数据
            }
        }
    };
}
```

### 2.2 交互流程解析

![bleClient](%E8%93%9D%E7%89%99%E3%80%81WIFI%E9%80%9A%E8%AE%AF%E5%8D%8F%E8%AE%AE.assets/bleClient.png)

![bleServer](%E8%93%9D%E7%89%99%E3%80%81WIFI%E9%80%9A%E8%AE%AF%E5%8D%8F%E8%AE%AE.assets/bleServer.png)

MA中为两个按钮，分别对应客户端和服务器端的活动跳转，以及动态权限的申请等

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <TextView
        android:id="@+id/tv_ble"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:text="低功耗蓝牙(BLE)"
        android:textStyle="bold"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btn_ble_client"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:onClick="bleClient"
        android:text="BLE客户端(主机/中心设备/Central)"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tv_ble" />

    <Button
        android:id="@+id/btn_ble_server"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:onClick="bleServer"
        android:text="BLE服务端(从机/外围设备/peripheral)"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/btn_ble_client" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

```java
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 检查蓝牙开关
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            Toast.makeText(this, "本机没有找到蓝牙硬件或驱动", Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else {
            if (!adapter.isEnabled()) {
                //直接开启蓝牙
                adapter.enable();
                //跳转到设置界面
                //startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 112);
            }
        }

        // 检查是否支持BLE蓝牙
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "本机不支持低功耗蓝牙", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Android 6.0动态请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.ACCESS_COARSE_LOCATION};
            for (String str : permissions) {
                if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissions, 111);
                    break;
                }
            }
        }
    }


    public void bleClient(View view) {
        startActivity(new Intent(this, BleClientActivity.class));
    }

    public void bleServer(View view) {
        startActivity(new Intent(this, BleServerActivity.class));
    }
}
```

divider.xml:

```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#666" />
    <size
        android:width="0.1dp"
        android:height="0.1dp" />
</shape>
```

sel_item.xml:

```xml
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_pressed="true">
        <color android:color="@color/colorAccent" />
    </item>
</selector>
```

stroke.xml:

```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <stroke
        android:width="1dp"
        android:color="@color/colorPrimaryDark" />
</shape>
```





### 2.3 客户端

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="2dp"
    tools:context=".ble.BleClientActivity">

    <Button
        android:id="@+id/btn_scan"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="reScan"
        android:text="重新扫描"
        tools:ignore="MissingConstraints" />

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv_ble"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:layout_below="@id/btn_scan"
        android:background="@drawable/stroke"
        android:padding="4dp" />


    <LinearLayout
        android:id="@+id/ll_character"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/rv_ble"
        android:orientation="horizontal">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Character:"
            android:textSize="20sp" />

        <EditText
            android:id="@+id/et_write"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:hint="最多20字节"
            android:inputType="none"
            android:maxLength="20" />

        <Button
            android:id="@+id/btn_read"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="read"
            android:text="读取"
            android:textAllCaps="false" />

        <Button
            android:id="@+id/btn_write"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="write"
            android:text="写入"
            android:textAllCaps="false" />
    </LinearLayout>

    <LinearLayout
        android:id="@+id/ll_descriptor"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/ll_character">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Descriptor:"
            android:textSize="20sp" />

        <EditText
            android:id="@+id/et_descriptor"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:hint="最多20字节"
            android:inputType="none"
            android:maxLength="20" />

        <Button
            android:id="@+id/btn_notify"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="setNotify"
            android:text="改变" />

        <Button
            android:id="@+id/btn_clear"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="clear"
            android:text="清屏" />
    </LinearLayout>

    <ScrollView
        android:id="@+id/scrollView2"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@id/ll_descriptor"
        android:background="@drawable/stroke"
        android:padding="2dp"
        android:scrollbars="none">

        <TextView
            android:id="@+id/tv_tips"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />
    </ScrollView>
</RelativeLayout>
```



```java
/**
 * BLE客户端(主机/中心设备/Central)
 */
public class BleClientActivity extends Activity {
    private static final String TAG = BleClientActivity.class.getSimpleName();
    private EditText mWriteET, et_descriptor;
    private TextView mTips;
    private BleDevAdapter mBleDevAdapter;
    private BluetoothGatt mBluetoothGatt; //客户端的蓝牙连接核心类
    private boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bleclient);

        RecyclerView rv = findViewById(R.id.rv_ble);
        mWriteET = findViewById(R.id.et_write);
        et_descriptor = findViewById(R.id.et_descriptor);
        mTips = findViewById(R.id.tv_tips);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //适配器的构造
        mBleDevAdapter = new BleDevAdapter(new BleDevAdapter.Listener() {
            //设置子项的点击事件
            @Override
            public void onItemClick(BluetoothDevice dev) {
                closeConn(); //释放旧的连接
                //连接当前选择的蓝牙设备，参数2表示只要BLE设备可用是否自动连接到它
                mBluetoothGatt = dev.connectGatt(BleClientActivity.this, false, mBluetoothGattCallback);
                logTv(String.format("与[%s]开始连接............", dev));
            }
        });
        rv.setAdapter(mBleDevAdapter);
    }

    // 扫描BLE
    public void reScan(View view) {
        if (mBleDevAdapter.isScanning) {
            Toast.makeText(this, "正在扫描……", Toast.LENGTH_SHORT).show();
        } else {
            mBleDevAdapter.reScan();
        }
    }

    /*
     * 注意：连续频繁读写数据容易失败，读写操作间隔最好200ms以上，或等待上次回调完成后再进行下次读写操作！
     */
    // 读取数据成功会回调->onCharacteristicRead()
    public void read(View view) {
        //getGattService为自定义的方法，通过UUID找到BluetoothGatt中对应的服务器端Service
        BluetoothGattService service = getGattService(BleServerActivity.UUID_SERVICE);
        if (service != null) {
            //再通过characteristic的UUID获取characteristic对象
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(BleServerActivity.UUID_CHAR_READ);
            //触发回调，实现读取
            mBluetoothGatt.readCharacteristic(characteristic);
        }
    }

    // 写入数据成功会回调->onCharacteristicWrite()
    public void write(View view) {
        BluetoothGattService service = getGattService(BleServerActivity.UUID_SERVICE);
        if (service != null) {
            String text = mWriteET.getText().toString();
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(BleServerActivity.UUID_CHAR_WRITE);//通过UUID获取可写的Characteristic
            //往characteristic中写入数据，单次最多20个字节
            characteristic.setValue(text.getBytes());
            mBluetoothGatt.writeCharacteristic(characteristic);
        }
    }

    // 设置通知，Characteristic发生变化会回调->onCharacteristicChanged()
    public void setNotify(View view) {
        BluetoothGattService service = getGattService(BleServerActivity.UUID_SERVICE);
        if (service != null) {
            //通过UUID获取到Characteristic对象，然后设置Characteristic的通知
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(BleServerActivity.UUID_CHAR_READ);
            mBluetoothGatt.setCharacteristicNotification(characteristic, true);

            // 通过UUID获取到descriptor对象，向Descriptor属性写入通知开关，使蓝牙设备主动向手机发送数据
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(BleServerActivity.UUID_DESC_NOTITY);
            // descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);//服务端不主动发数据,只指示客户端读取数据
//            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            String des_value = et_descriptor.getText().toString();
            if (!TextUtils.isEmpty(des_value)) {
                descriptor.setValue(des_value.getBytes());
            }
            //触发onDescriptorWrite
            mBluetoothGatt.writeDescriptor(descriptor);
        }
    }


    public void clear(View view) {
        mTips.setText("");
    }

    // 获取Gatt服务
    private BluetoothGattService getGattService(UUID uuid) {
        if (!isConnected) {
            Toast.makeText(this, "没有连接", Toast.LENGTH_SHORT).show();
            return null;
        }
        BluetoothGattService service = mBluetoothGatt.getService(uuid);
        if (service == null) {
            Toast.makeText(this, "没有找到服务UUID=" + uuid, Toast.LENGTH_SHORT).show();
        }
        return service;
    }

    // 再TV上打印日志信息
    private void logTv(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTips.append(msg + "\n\n");
            }
        });
    }

    // BLE中心设备连接外围设备的数量有限(大概2~7个)，在建立新连接之前必须释放旧连接资源，否则容易出现连接错误133
    private void closeConn() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeConn();
    }

    // 与服务端连接的Callback
    public BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {
        //连接状态变化时回调
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            BluetoothDevice dev = gatt.getDevice();//获取连接的目标蓝牙设备
            Log.e(TAG, String.format("状态发生变化:%s,%s,%s,%s", dev.getName(), dev.getAddress(), status, newState));
            //如果连接成功
            if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_CONNECTED) {
                isConnected = true;
                gatt.discoverServices(); //发现可用的蓝牙服务
            } else {
                isConnected = false;
                closeConn();
            }
            //tv上显示连接变化的内容
            logTv(String.format(status == 0 ? (newState == 2 ? "与[%s]连接成功" : "与[%s]连接断开") : ("与[%s]连接出错,错误码:" + status), dev));
        }

        //发现蓝牙服务，在蓝牙连接成功时被调用
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.e(TAG, String.format("发现服务:%s,%s,%s", gatt.getDevice().getName(), gatt.getDevice().getAddress(), status));
            if (status == BluetoothGatt.GATT_SUCCESS) { //BLE服务发现成功
                // 遍历获取BLE服务Services的全部UUID
                for (BluetoothGattService service : gatt.getServices()) {
                    StringBuilder allUUIDs = new StringBuilder("UUIDs={\nS=" + service.getUuid().toString());
                    //遍历获取Characteristics的全部UUID
                    for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                        allUUIDs.append(",\nC=").append(characteristic.getUuid());
                        //遍历获取Descriptors的全部UUID
                        for (BluetoothGattDescriptor descriptor : characteristic.getDescriptors())
                            allUUIDs.append(",\nD=").append(descriptor.getUuid());
                    }
                    allUUIDs.append("}");
                    logTv("发现服务" + allUUIDs);
                }
            }
        }

        //读取characteristic时回调
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            //获取到Characteristic的UUID和Value
            UUID uuid = characteristic.getUuid();
            String valueStr = new String(characteristic.getValue());
            logTv("从服务器端" + gatt.getDevice().getName() + "，UUID为[" + uuid + "]的Characteristic读取到的Value为:" + valueStr);
        }

        //写入characteristic时回调，此方法中只负责打印了数据
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            UUID uuid = characteristic.getUuid();
            String valueStr = new String(characteristic.getValue());
            logTv("向服务器端" + gatt.getDevice().getName() + "，UUID为[" + uuid + "]的Characteristic写入Value:" + valueStr);
        }

        //当特征值（Characteristic）改变时回调
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            UUID uuid = characteristic.getUuid();
            String valueStr = new String(characteristic.getValue());
            Log.e(TAG, String.format("onCharacteristicChanged:%s,%s,%s,%s", gatt.getDevice().getName(), gatt.getDevice().getAddress(), uuid, valueStr));
            logTv("服务器端" + gatt.getDevice().getName() + "，UUID为[" + uuid + "]的Characteristic发生了改变:\n改变后的Value值为:" + valueStr);
        }

        //读取descriptor时回调
        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            UUID uuid = descriptor.getUuid();
            String valueStr = Arrays.toString(descriptor.getValue());
            Log.e(TAG, String.format("onDescriptorRead:%s,%s,%s,%s,%s", gatt.getDevice().getName(), gatt.getDevice().getAddress(), uuid, valueStr, status));
            logTv("读取Descriptor[" + uuid + "]:\n，读取的Value值:" + valueStr);
        }

        //写入descriptor时回调
        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            UUID uuid = descriptor.getUuid();
            byte[] value = descriptor.getValue();

            Log.e(TAG, String.valueOf(value.length));
            Log.e(TAG, value.toString() );
            logTv("本机改变了UUID为[" + uuid + "]的descriptor，改变后的Value值为:" +value);
        }
    };
}
```



### 2.4 服务器端

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="2dp"
    tools:context=".ble.BleServerActivity">

    <LinearLayout
        android:id="@+id/ll_character"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Character:"
            android:textSize="20sp" />

        <EditText
            android:id="@+id/et_read"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:hint="在此输入客户端读取的数据"
            android:maxLines="20" />
    </LinearLayout>


    <ScrollView
        android:id="@+id/sv_tv"
        android:layout_width="match_parent"
        android:layout_height="600dp"
        android:layout_below="@id/ll_character"
        android:background="@drawable/stroke"
        android:padding="2dp"
        android:scrollbars="none">

        <TextView
            android:id="@+id/tv_tips"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />
    </ScrollView>

    <Button
        android:id="@+id/btn_clear"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/sv_tv"
        android:layout_alignParentEnd="true"
        android:onClick="clear"
        android:text="清屏" />
</RelativeLayout>
```



```java
/**
 * BLE服务端(从机/外围设备/peripheral)
 */
public class BleServerActivity extends Activity {
    //自定义一些UUID
    public static final UUID UUID_SERVICE = UUID.fromString("10000000-0000-0000-0000-000000000000"); //服务的UUID
    public static final UUID UUID_CHAR_READ = UUID.fromString("11000000-0000-0000-0000-000000000000");//读取character的UUID
    public static final UUID UUID_CHAR_WRITE = UUID.fromString("12000000-0000-0000-0000-000000000000"); //写入character的UUID
    public static final UUID UUID_DESC_NOTITY = UUID.fromString("11100000-0000-0000-0000-000000000000");  //写入descrpitor的UUID

    private static final String TAG = BleServerActivity.class.getSimpleName();
    private TextView mTips;
    private EditText et_read;

    private BluetoothLeAdvertiser mBluetoothLeAdvertiser; // BLE广播
    private BluetoothGattServer mBluetoothGattServer; // BLE服务端

    public void clear(View view) {
        mTips.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bleserver);
        mTips = findViewById(R.id.tv_tips);
        et_read = findViewById(R.id.et_read);

        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // ============启动BLE蓝牙广播(广告) =================================================================================
        //广播设置(必须)
        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY) //广播模式: 低功耗,平衡,低延迟
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH) //发射功率级别: 极低,低,中,高
                .setConnectable(true) //能否连接,广播分为可连接广播和不可连接广播
                .build();
        //广播数据(必须，广播启动就会发送)
        AdvertiseData advertiseData = new AdvertiseData.Builder()
                .setIncludeDeviceName(true) //包含蓝牙名称
                .setIncludeTxPowerLevel(true) //包含发射功率级别
                .addManufacturerData(1, new byte[]{23, 33}) //设备厂商数据，自定义
                .build();
        //扫描响应数据(可选，当客户端扫描时才发送)
        AdvertiseData scanResponse = new AdvertiseData.Builder()
                .addManufacturerData(2, new byte[]{66, 66}) //设备厂商数据，自定义
                .addServiceUuid(new ParcelUuid(UUID_SERVICE)) //服务UUID
//                .addServiceData(new ParcelUuid(UUID_SERVICE), new byte[]{2}) //服务数据，自定义
                .build();
        mBluetoothLeAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
        mBluetoothLeAdvertiser.startAdvertising(settings, advertiseData, scanResponse, mAdvertiseCallback);

        // 注意：必须要开启可连接的BLE广播，其它设备才能发现并连接BLE服务端!
        // =============启动BLE蓝牙服务端=====================================================================================
        BluetoothGattService service = new BluetoothGattService(UUID_SERVICE, BluetoothGattService.SERVICE_TYPE_PRIMARY);
        //添加可读characteristic
        BluetoothGattCharacteristic characteristicRead = new BluetoothGattCharacteristic(UUID_CHAR_READ,
                BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PERMISSION_READ);
        //给characteristic添加可写入的Descriptor
        characteristicRead.addDescriptor(new BluetoothGattDescriptor(UUID_DESC_NOTITY, BluetoothGattCharacteristic.PERMISSION_WRITE));
        service.addCharacteristic(characteristicRead);
        //添加可写characteristic
        BluetoothGattCharacteristic characteristicWrite = new BluetoothGattCharacteristic(UUID_CHAR_WRITE,
                BluetoothGattCharacteristic.PROPERTY_WRITE, BluetoothGattCharacteristic.PERMISSION_WRITE);
        service.addCharacteristic(characteristicWrite);
        if (bluetoothManager != null)
            mBluetoothGattServer = bluetoothManager.openGattServer(this, mBluetoothGattServerCallback);
        mBluetoothGattServer.addService(service);
    }

    // BLE广播Callback
    private AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            logTv("BLE广播开启成功");
        }

        @Override
        public void onStartFailure(int errorCode) {
            logTv("BLE广播开启失败,错误码:" + errorCode);
        }
    };

    // BLE服务端Callback
    private BluetoothGattServerCallback mBluetoothGattServerCallback = new BluetoothGattServerCallback() {
        //连接状态改变时被调用
        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            Log.i(TAG, String.format("连接状态改变:%s,%s,%s,%s", device.getName(), device.getAddress(), status, newState));
            logTv(String.format(status == 0 ? (newState == 2 ? "与[%s]连接成功" : "与[%s]连接断开") : ("与[%s]连接出错,错误码:" + status), device));
        }

        //添加服务成功，连接后自动被调用
        @Override
        public void onServiceAdded(int status, BluetoothGattService service) {
            logTv(String.format(status == 0 ? "添加服务[%s]成功" : "添加服务[%s]失败,错误码:" + status, service.getUuid()));
        }

        //客户端读取Characteristic的响应
        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
            String response = et_read.getText().toString(); //模拟数据
            if (!TextUtils.isEmpty(response)) {
                mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, response.getBytes());// 响应客户端
                logTv("客户端" + device.getName() + "，UUID为[" + characteristic.getUuid() + "]读取Characteristic:" + response);
            }
        }

        //客户端写入Characteristic的响应
        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] requestBytes) {
            String requestStr = new String(requestBytes);
            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, requestBytes);// 响应客户端
            logTv("客户端" + device.getName() + "向UUID为[" + characteristic.getUuid() + "]写入Characteristic：" + requestStr);
        }

        //客户端读取Descriptor时的响应
        @Override
        public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
            String response = "DESC_" + (int) (Math.random() * 100); //模拟数据
            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, response.getBytes()); // 响应客户端
            logTv("客户端读取Descriptor[" + descriptor.getUuid() + "]:\n" + response);
        }

        //客户端写入Descriptor时的响应
        @Override
        public void onDescriptorWriteRequest(final BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            String valueStr = Arrays.toString(value);            // 获取客户端发过来的数据
            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);// 响应客户端
            logTv("客户端"+device.getName()+"向UUID为[" + descriptor.getUuid() + "]，写入Descriptor:\n" + valueStr);

         /*   // 简单模拟通知客户端Characteristic变化
            if (Arrays.toString(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE).equals(valueStr)) { //是否开启通知
                final BluetoothGattCharacteristic characteristic = descriptor.getCharacteristic();
                String response = "CHAR_" + (int) (Math.random() * 100); //模拟数据
                characteristic.setValue(response);
                mBluetoothGattServer.notifyCharacteristicChanged(device, characteristic, false);
                logTv("通知客户端改变Characteristic[" + characteristic.getUuid() + "]:\n" + response);
            }*/
        }

        @Override
        public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
            Log.i(TAG, String.format("onExecuteWrite:%s,%s,%s,%s", device.getName(), device.getAddress(), requestId, execute));
        }

        @Override
        public void onNotificationSent(BluetoothDevice device, int status) {
            Log.i(TAG, String.format("onNotificationSent:%s,%s,%s", device.getName(), device.getAddress(), status));
        }

        @Override
        public void onMtuChanged(BluetoothDevice device, int mtu) {
            Log.i(TAG, String.format("onMtuChanged:%s,%s,%s", device.getName(), device.getAddress(), mtu));
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothLeAdvertiser != null)
            mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
        if (mBluetoothGattServer != null)
            mBluetoothGattServer.close();
    }

    private void logTv(final String msg) {
        if (isDestroyed())
            return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(BleServerActivity.this, msg, Toast.LENGTH_SHORT).show();
                mTips.append(msg + "\n\n");
            }
        });
    }
}
```



## 3 细节问题

### 3.1 广播多发

服务器端的广播，第二次及以上发送，会回调发送失败的函数



### 3.2 解决一次只能传输20个字节的问题：

```java
 public void write(View view) {
        BluetoothGattService service = getGattService(BleServerActivity.UUID_SERVICE);
        if (service != null) {
            String text = mWriteET.getText().toString();
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(BleServerActivity.UUID_CHAR_WRITE);//通过UUID获取可写的Characteristic

            //将输入的内容转换成byte数组
            byte[] all_bytes = text.getBytes();

            //用队列获取数据
            Queue<Byte> queue = new LinkedList<>();
            for (byte bytes : all_bytes) {
                queue.add(bytes);
            }

            //需要遍历几次输出
            int count = all_bytes.length % 18 == 0 ? all_bytes.length / 18 : (all_bytes.length / 18) + 1;

            //遍历数组
            for (int i = 0; i < count; i++) {
                byte[] tmp = new byte[20];
                for (int j = 0; j < 18; j++) {
                    //最后一个元素时退出
                    if (i * 18 + j == all_bytes.length) {
                        break;
                    }
                    tmp[j] = queue.peek(); //取出队列中的元素
                    queue.poll(); //删除该元素
                }
                Log.e(TAG, Arrays.toString(tmp));
                characteristic.setValue(tmp);
                mBluetoothGatt.writeCharacteristic(characteristic);

                try {
                    Thread.sleep(200);  //因为写入回调不能过于频繁，让系统休眠200ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
```





# 三、Wifi

## 1 常见的类

| 类或接口          | 描述                                                         |
| ----------------- | ------------------------------------------------------------ |
| ScanResult        | 描述已经检测出的接入点，包括接入点的地址、名称、身份认证、频率、信号强度等 |
| WifiConfiguration | 包含了Wifi的网络配置和信息                                   |
| WifiInfo          | Wifi连接的描述，包括接入点、网络状态、隐藏的接入点、IP地址、连接速度、MAC地址、网络ID、信号强度等 |
| WifiManager       | Wifi连接的统一管理类，有一系列的常量表示Wifi网卡的状态       |



### 3.1 WifiConfiguration

主要包含四个属性：

| 属性         | 描述                                                         |
| ------------ | ------------------------------------------------------------ |
| BSSID        | BSS是一种特殊的支持点对点访问的无线网络模式的应用，BSSID在手机WIFI中就是无线路由的MAC地址 |
| NetWorkID    | 网络ID                                                       |
| PreSharedKey | 无线网络的安全认证模式                                       |
| SSID         | WIFI名称，标识无线局域网，SSID不同的无线网络是无法进行互访的 |





### 3.2 WifiInfo

可以通过一系列的getXXX 获取无线连接的信息:

```java
System.out.println(mWifiInfo.getBSSID());       // 02:00:00:00:00:00
System.out.println(mWifiInfo.getSSID());        //<unknown ssid>
System.out.println(mWifiInfo.getIpAddress());   //1536164874
System.out.println(mWifiInfo.getMacAddress());  // 02:00:00:00:00:00
System.out.println(mWifiInfo.getRssi());        //-44
System.out.println(mWifiInfo.getLinkSpeed());   //400
System.out.println(mWifiInfo.getNetworkId());   //-1
```



### 3.3 WifiManager

常量信息:

| 常量值                   | 描述             |
| ------------------------ | ---------------- |
| WIFI_STATE_DISABLING = 0 | WIFI网卡正在关闭 |
| WIFI_STATE_DISABLED = 1  | WIFI网卡不可用   |
| WIFI_STATE_ENABLING = 2  | WIFI网卡正在打开 |
| WIFI_STATE_ENABLED = 3   | WIFI网卡可用     |
| WIFI_STATE_UNKNOWN = 4   | 未知网卡状态     |



### 3.4 权限

```xml
   <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <uses-permission android:name="android.permission.INTERNET"/>
```



## 2 开启、扫描

### 2.1 开启Wifi

API 29 以后，不允许应用打开Wifi

```java
    //如果WIFI未启用则打开WIFI，并断开已连接的WIFI
    if (!mWifiManager.isWifiEnabled()) {
         mWifiManager.setWifiEnabled(true);
	}
```

### 2.2 获取Wifi管理类

```java
WifiManager mWifiManager;
mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
```





### 2.3 扫描

开启扫描：

```java
mWifiManager.startScan();
```

广播接收扫描结果：

```java
 private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case WifiManager.SCAN_RESULTS_AVAILABLE_ACTION:
                    List<ScanResult> scanResults = mWifiManager.getScanResults();
                    rv_wifi.setAdapter(new WifiAdapter(scanResults, mWifiManager));
                    break;
            }
        }
    };

    //广播的过滤器
    private IntentFilter WIFI_IntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        return intentFilter;
    }
```

注册和销毁广播：

```java
registerReceiver(mReceiver, WIFI_IntentFilter());   //注册广播

	@Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
```



### 2.4 适配器和连接：

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <TextView
        android:id="@+id/SSID"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="#E91E63" />

    <TextView
        android:id="@+id/level"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

    <TextView
        android:layout_width="match_parent"
        android:layout_height="2px"
        android:background="#000000" />
</LinearLayout>
```

```java
public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.ViewHoder> {
    private List<ScanResult> mWifiList;
    private WifiManager mWifiManager;

    public WifiAdapter(List<ScanResult> wifiList, WifiManager wifiManager) {
        mWifiList = wifiList;
        mWifiManager = wifiManager;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder viewHoder, int i) {
        String SSID = mWifiList.get(i).SSID;
        viewHoder.item_name.setText(SSID);
        int level = mWifiList.get(i).level;
        String str_level;
        if (level <= 0 && level >= -50) {
            str_level = "信号很好";
        } else if (level < -50 && level >= -80) {
            str_level = "信号一般";
        } else {
            str_level = "信号较差";
        }
        viewHoder.item_level.setText(str_level);
    }

    @Override
    public int getItemCount() {
        return mWifiList.size();
    }

    static class ViewHoder extends RecyclerView.ViewHolder {
        TextView item_name;
        TextView item_level;

        public ViewHoder(@NonNull View view) {
            super(view);
            item_name = view.findViewById(R.id.SSID);
            item_level = view.findViewById(R.id.level); //信号水平
        }
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wifi, parent, false);
        final RecyclerView.ViewHolder holder = new ViewHoder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果WIFI未启用则打开WIFI，并断开已连接的WIFI
                if (!mWifiManager.isWifiEnabled()) {
                    mWifiManager.setWifiEnabled(true);
                }
                mWifiManager.disconnect();//如果之前连接了WIFI则断开
                int position = holder.getAdapterPosition();
                ScanResult scanResult = mWifiList.get(position);
                craeteDialogAndConnect(view.getContext(), scanResult.SSID); //自定义方法，根据SSID弹出输入密码对话框，进行连接
            }
        });
        return (ViewHoder) holder;
    }


    /*
     * 展示对话框并且连接
     * */
    public void craeteDialogAndConnect(final Context context, final String ssid) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(ssid);
        alert.setMessage("输入密码");
        final EditText et_password = new EditText(context);
        alert.setView(et_password);

        alert.setPositiveButton("连接", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = et_password.getText().toString();
                if (null == password || password.length() < 8) {
                    Toast.makeText(context, "密码至少8位", Toast.LENGTH_SHORT).show();
                    return;
                }
                //进行连接
                WifiConfiguration wifiInfo = createWifiInfo(ssid, password, 3);
                mWifiManager.addNetwork(wifiInfo);
            }
        });
        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    /*
     * 通过UUID\密码\加密类型 创建出WifiConfiguration对象
     * */
    public WifiConfiguration createWifiInfo(String SSID, String Password, int Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        //判断是否有以前的连接配置
        WifiConfiguration tempConfig = this.IsExsits(SSID);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }

        //没有密码的模式
        if (Type == 1) //WIFICIPHER_NOPASS
        {
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }

        //有线等效加密，
        if (Type == 2) //WIFICIPHER_WEP
        {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + Password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }


        /*
         * 无限加密，wifi访问保护，是目前使用最普遍的模式
         * 还有WPA2模式，使用CCMP替代TKIP，它是目前使用最广泛的无限加密方式
         * */
        if (Type == 3) //WIFICIPHER_WPA
        {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }

        return config;
    }


    /*
     * 如果存在之前的连接配置，直接使用之前的连接配置信息
     * */
    private WifiConfiguration IsExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }
}
```



```java
RecyclerView rv_wifi;
rv_wifi = findViewById(R.id.rv_wifi);
rv_wifi.setLayoutManager(new LinearLayoutManager(this));
```





### 2.5 检查当前Wifi是否连接

```java
  public void check(View view) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifi.isConnected()) {
            Toast.makeText(this, "连接", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未连接", Toast.LENGTH_SHORT).show();
        }
    }
```



### 2.6 获取当前的IP地址

```java
ip = intToIp(mWifiManager.getConnectionInfo().getIpAddress());

 //把IP转化成 A.B.C.D的形式
    private String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }
```



## 3 通信

Wifi 通信就是传统的Socket通信，流程与传统蓝牙基本一致，服务器根据端口号建立ServerSocket，客户端根据端口号和服务器IP 建立Socket ，利用IO流获取输入输出信息

为了方便，这里只写了客户端向服务器端发送数据的流程

### 3.1 服务端线程

```java
public class ListenerThread extends Thread {
    private ServerSocket serverSocket = null;
    private Handler handler;
    private int port;
    private Socket socket;

    public ListenerThread(int port, Handler handler) {
        setName("ListenerThread");
        this.port = port;
        this.handler = handler;
        try {
            //根据端口号创建服务器端Socket，并发送消息更新UI
            serverSocket = new ServerSocket(port);
            Message message = Message.obtain();
            message.what = MainActivity.SERVER_FINSHED;
            handler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (true) {
            try {
                //阻塞，等待设备连接
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (socket != null) {
                //提示有设备接入
                Message message = Message.obtain();
                message.what = MainActivity.DEVICE_CONNECTED;
                handler.sendMessage(message);
                try {
                    InputStream in = socket.getInputStream();
                    //连接时循环读取
                    byte[] buffer = new byte[1024];  // 用于流的缓冲存储
                    // 从InputStream读取数据
                    int length = in.read(buffer);
                    String str = new String(buffer, 0, length, "utf-8");
                    //UI更新显示收到的数据
                    Message message1 = Message.obtain();
                    message1.what = MainActivity.GET_MSG;
                    Bundle bundle = new Bundle();
                    bundle.putString("MSG",str);
                    message1.setData(bundle);
                    handler.sendMessage(message1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```



```java
  ListenerThread listenerThread;

    public void createServerSocket(View view) {
        listenerThread = new ListenerThread(64542, handler);	//端口号任意0~65535，不被占用即可
        listenerThread.start();
    }
```





### 3.2 客户端线程

```java
public class ConnectThread extends Thread {
    private Socket socket;
    private Handler handler;
    private OutputStream outputStream;

    public ConnectThread(String ip, int port, Handler handler) {
        setName("ConnectThread");
        try {
            socket = new Socket(ip,port);
            socket.setSoTimeout(60000);
            //发送消息，客户端构建成功
            Message message = Message.obtain();
            message.what = MainActivity.CLIENT_FINSHED;
            handler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            //获取数据流
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送数据
     */
    public void sendData(String msg) {
        if (outputStream != null) {
            try {
                outputStream.write(msg.getBytes());
                Message message = Message.obtain();
                message.what = MainActivity.SEND_MSG_SUCCESS;
                Bundle bundle = new Bundle();
                bundle.putString("MSG", new String(msg));
                message.setData(bundle);
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```



```java
    ConnectThread connectThread;

	public void createClientSocket(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectThread = new ConnectThread("192.168.43.26", 64542, handler);	//服务端的IP和端口号
                connectThread.start();
            }
        }).start();
    }
```



### 3.3 通信和显示

向服务器端发送数据：

```java
    EditText et_msg;
	et_msg = findViewById(R.id.et_text);
    
	public void sendMsg(View view) {
        String msg = et_msg.getText().toString();
        if (connectThread != null) {
            connectThread.sendData(msg);
        }
    }
```



显示当前的操作和获取的数据：

```java
    public static final int SERVER_FINSHED = 1;
    public static final int DEVICE_CONNECTED = 2;
    public static final int GET_MSG = 3;
    public static final int SEND_MSG_SUCCESS = 4;
    public static final int SEND_MSG_ERROR = 5;
    public static final int CLIENT_FINSHED = 6;

    TextView tv_operation;
    tv_operation = findViewById(R.id.tv_operation);
     
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //当服务器端开启时，新建客户端线程
                case SERVER_FINSHED:
                    tv_operation.setText("服务器端已启动，持续监听中……");
                    break;
                case DEVICE_CONNECTED:
                    tv_operation.setText("已连接");
                    break;
                case GET_MSG:
                    Bundle bundle = msg.getData();
                    String MSG = bundle.getString("MSG");
                    tv_operation.setText("收到消息:" + MSG);
                    break;
                case SEND_MSG_SUCCESS:
                    tv_operation.setText("发送成功");
                    break;
                case SEND_MSG_ERROR:
                    tv_operation.setText("发送消息失败");
                    break;
                case CLIENT_FINSHED:
                    tv_operation.setText("客户端构建成功");
                    break;
            }
        }
    };
```

















































