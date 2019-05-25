/*
package cn.algerfan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

public class MultiPartDownLoad {

    private static Logger logger = LoggerFactory.getLogger(MultiPartDownLoad.class);

    */
/**
     * 线程下载成功标志
     *//*

    private static int flag = 0;

    */
/**
     * 服务器请求路径
     *//*

    private String serverPath;
    */
/**
     * 本地路径
     *//*

    private String localPath;
    */
/**
     * 线程计数同步辅助
     *//*

    private CountDownLatch latch;
    */
/**
     * 定长线程池
     *//*

    private static ExecutorService threadPool;

    public MultiPartDownLoad(String serverPath, String localPath) {
        this.serverPath = serverPath;
        this.localPath = localPath;
    }

    public boolean executeDownLoad() {
        try {
            URL url = new URL(serverPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时时间
            conn.setConnectTimeout(5000);
            //设置请求方式
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Connection", "Keep-Alive");
            int code = conn.getResponseCode();
            if (code != 200) {
                logger.error(String.format("无效网络地址：%s", serverPath));
                return false;
            }
            //服务器返回的数据的长度，实际上就是文件的长度,单位是字节
            // int length = conn.getContentLength();  //文件超过2G会有问题
            long length = getRemoteFileSize(serverPath);

            logger.info("文件总长度:" + length + "字节(B)");
            RandomAccessFile raf = new RandomAccessFile(localPath, "rwd");
            //指定创建的文件的长度
            raf.setLength(length);
            raf.close();
            //分割文件
            int partCount = Constans.MAX_THREAD_COUNT;
            int partSize = (int)(length / partCount);
            latch = new CountDownLatch(partCount);
            threadPool = Constans.getMyThreadPool();
            for (int threadId = 1; threadId <= partCount; threadId++) {
                // 每一个线程下载的开始位置
                long startIndex = (threadId - 1) * partSize;
                // 每一个线程下载的开始位置
                long endIndex = startIndex + partSize - 1;
                if (threadId == partCount) {
                    //最后一个线程下载的长度稍微长一点
                    endIndex = length;
                }
                logger.info("线程" + threadId + "下载:" + startIndex + "字节~" + endIndex + "字节");
                threadPool.execute(new DownLoadThread(threadId, startIndex, endIndex, latch));
            }
            latch.await();
            if(flag == 0){
                return true;
            }
        } catch (Exception e) {
            logger.error(String.format("文件下载失败，文件地址：%s,失败原因：%s", serverPath, e.getMessage()), e);
        }
        return false;
    }


    */
/**
     * 内部类用于实现下载
     *//*

    public class DownLoadThread implements Runnable {
        private Logger logger = LoggerFactory.getLogger(DownLoadThread.class);

        */
/**
         * 线程ID
         *//*

        private int threadId;
        */
/**
         * 下载起始位置
         *//*

        private long startIndex;
        */
/**
         * 下载结束位置
         *//*

        private long endIndex;

        private CountDownLatch latch;

        DownLoadThread(int threadId, long startIndex, long endIndex, CountDownLatch latch) {
            this.threadId = threadId;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                logger.info("线程" + threadId + "正在下载...");
                URL url = new URL(serverPath);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestMethod("GET");
                //请求服务器下载部分的文件的指定位置
                conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
                conn.setConnectTimeout(5000);
                int code = conn.getResponseCode();
                logger.info("线程" + threadId + "请求返回code=" + code);
                //返回资源
                InputStream is = conn.getInputStream();
                RandomAccessFile raf = new RandomAccessFile(localPath, "rwd");
                //随机写文件的时候从哪个位置开始写
                //定位文件
                raf.seek(startIndex);
                int len;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    raf.write(buffer, 0, len);
                }
                is.close();
                raf.close();
                logger.info("线程" + threadId + "下载完毕");
            } catch (Exception e) {
                //线程下载出错
                MultiPartDownLoad.flag = 1;
                logger.error(e.getMessage(),e);
            } finally {
                //计数值减一
                latch.countDown();
            }

        }
    }

    */
/**
     * 内部方法，获取远程文件大小
     * @param remoteFileUrl
     * @return
     * @throws IOException
     *//*

    private long getRemoteFileSize(String remoteFileUrl) throws IOException {
        long fileSize;
        HttpURLConnection httpConnection = (HttpURLConnection) new URL(remoteFileUrl).openConnection();
        httpConnection.setRequestMethod("HEAD");
        int responseCode = 0;
        try {
            responseCode = httpConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (responseCode >= 400) {
            logger.debug("Web服务器响应错误!");
            return 0;
        }
        String sHeader;
        for (int i = 1;; i++) {
            sHeader = httpConnection.getHeaderFieldKey(i);
            if ("Content-Length".equals(sHeader)) {
                fileSize = Long.parseLong(httpConnection.getHeaderField(sHeader));
                break;
            }
        }
        return fileSize;
    }

    */
/**
     * 下载文件执行器
     * @param serverPath
     * @return
     *//*

    public synchronized static String downLoad(String serverPath) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();

        String[] names = serverPath.split("\\.");
        if (names.length <= 0) {
            return null;
        }
        String fileTypeName = names[names.length - 1];
        String localPath = String.format("/%s.%s", UUID.randomUUID(),fileTypeName);
        MultiPartDownLoad m = new MultiPartDownLoad(serverPath, localPath);
        long startTime = System.currentTimeMillis();
        boolean flag = false;
        try{
            flag = m.executeDownLoad();
            long endTime = System.currentTimeMillis();
            if(flag){
                logger.info("文件下载结束,共耗时" + (endTime - startTime)+ "ms");
                return localPath;
            }
            logger.warn("文件下载失败");
            return null;
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex);
            return null;
        }finally {
            // 重置 下载状态
            MultiPartDownLoad.flag = 0;
            if(!flag){
                File file = new File(localPath);
                file.delete();
            }
            lock.unlock();
        }
    }
}
*/
