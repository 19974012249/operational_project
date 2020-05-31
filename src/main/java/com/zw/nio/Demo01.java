package com.zw.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author zhouwei
 * @date 2020-25-22:04
 */
public class Demo01 {

    public static void main(String[] args) throws IOException {

        Integer integer = new Integer(100);
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        socketChannel.configureBlocking(false);
        // 分配容量为1024的缓冲区`，并将当前位置重置为0
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 返回buffer的capacity的大小
        int capacity = buffer.capacity();
        // 判断缓冲区是否还有元素
        boolean b = buffer.hasRemaining();
        // 返回buffer的 limit
        int limit = buffer.limit();
        // 设置缓冲区的limit，并返回一个设置了limit的buffer
        Buffer limit1 = buffer.limit(512);
        // 对缓冲区设置标志
        Buffer mark = buffer.mark();
        // 返回缓冲区当前的位置
        int position1 = buffer.position();
        // 将缓冲区的当前位置设置为100，并返回修改的buffer对象
        Buffer position = buffer.position(100);
        // 返回position和limit之间的元素个数
        int remaining = buffer.remaining();
        // 将位置position转到以前设置的mark所在的位置
        Buffer reset = buffer.reset();
        // 将位置设置成0，取消设置的mark、
        Buffer rewind = buffer.rewind();
        String username = UUID.randomUUID().toString().replace("-", "");
        // 发数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String content = scanner.nextLine();
            // 通过put方法写入数据到缓存区
            buffer.put(("[" + LocalDateTime.now().toString() + "]" + username + "说: " + content).getBytes());
            // 切换读写数据模式
            buffer.flip();
            socketChannel.write(buffer);
            //            socketChannel.shutdownOutput();
            // 清空缓冲区并返回对缓冲区的引用
            buffer.clear();
        }
        socketChannel.close();
    }

}

// 服务端接收信息
class NIOServer {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(9898));
        Selector selector = Selector.open();
        //注册选择器
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        //手动轮询
        //当前线程阻塞
        while (selector.select() > 0) {
            //阻塞操作,获取选择器上已经"准备就绪"的事件,当至少有一个通道被选择时才返回
            //获取多路选择器上的已选择的键集，这个键集是就绪状态的通道的集合，
            // 还有一个方法叫keys()返回的是注册到这个多路复选器上的键，不管是就绪状态的还是非就绪状态的。
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey sk = keyIterator.next();
                if (sk.isAcceptable()) {//如果有连接
                    SocketChannel socketChannel = ssc.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    new Thread(new NIOServerRead(sk)).start();
                }
                keyIterator.remove();
            }
        }
        //关闭通道
        //ssc.close();
    }
}

class NIOServerRead implements Runnable {

    //
    SelectionKey selectionKey;

    SocketChannel socketChannel;

    public NIOServerRead(SelectionKey selectionKey) {

        this.selectionKey = selectionKey;
        socketChannel = (SocketChannel) selectionKey.channel();
    }

    @Override
    public void run() {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            int len = 0;
            while ((len = socketChannel.read(buffer)) > 0) {
                buffer.flip();
                System.out.println(new String(buffer.array(), 0, len));
                buffer.clear();
            }
        } catch (IOException e) {
            selectionKey.cancel();
            try {
                socketChannel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
