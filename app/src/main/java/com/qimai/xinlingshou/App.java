package com.qimai.xinlingshou;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.dianping.logan.Logan;
import com.dianping.logan.LoganConfig;
import com.qimai.xinlingshou.utils.AidlUtil;
import com.qimai.xinlingshou.utils.Store;
import com.qimai.xinlingshou.utils.ToastUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2018/5/18.
 */

public class App extends Application {

    private static final String TAG = "App";

    public  static   DbManager db;
    public static Store store;
    public static Context mContext;
/*
     正式
    public static String API_URL = "http://inapi.zvcms.com/web/";
    public static String APP_URL = "http://inapi.zvcms.com/";
    public static final String WEBSOCKET_HOST_AND_PORT = "ws://gw.wm.qmai.cn:8282";//可替换为自己的主机名和端口号
     内部测试
    http://account-server.zvcms.com/seller/account/login
*/


  //  http://inapi.qimai.shop/web


    // 内部zvcms环境
	//public static String API_URL = "http://inapi.zvcms.com/";
    // 内部zmcms环境
	//public static String API_URL = "http://inapi.zmcms.cn/web/";
    // 线上shop环境`
	public static String API_URL = "http://inapi.qimai.shop/web/";
    // 内部beta环境x
    //public static String API_URL = "http://inapi.qmai.co/web/";

	//13136571734 123456

    //13074634093 111111
    //18860470317  123456
    // 线上正式环境
	//public static String API_URL = "http://inapi.qmai.cn/web/";
	//public static String API_URL = "http://inapi.qmai.cn/web/";

	//public static String API_URL = "http://inapi.qmai.cn/web/";

    //  收银机订单 用于接收收银机收款后产生的订单
    public static final String API_RECEIVE = "ptfw/order/receive";

    public static App instance = null;


    private boolean isAidl;

    public boolean isAidl() {

        return isAidl;
    }

    public void setAidl(boolean aidl) {
        isAidl = aidl;
    }



    @Override
    public void onCreate() {

        //初始化XUtils
        x.Ext.init(this);
        //设置debug模式
        x.Ext.setDebug(true);
        LitePal.initialize(this);
        store = Store.init(this, "app", Context.MODE_APPEND);
        instance = this;
        isAidl = true;
        AidlUtil.getInstance().connectPrinterService(this);

        initMeituanLog();

        //本地数据的初始化
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("qm_db.db") //设置数据库名
                .setDbVersion(1) //设置数据库版本,每次启动应用时将会检查该版本号,
                // 发现数据库版本低于这里设置的值将进行数据库升级并触发DbUpgradeListener
                .setAllowTransaction(true) //设置是否开启事务,默认为false关闭事务
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager dbManager, TableEntity<?> tableEntity) {
                    }
                })
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                // 设置数据库创建时的Listener
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                        // or
                        // db.dropDb();
                    }
                }); //设置数据库升级时的Listener,这里可以执行相关数据库表的相关修改,比如alter语句增加字段等
//         .setDbDir(null);//设置数据库.db文件存放的目录,默认为包名下databases目录下

         db = x.getDb(daoConfig);
        super.onCreate();
        mContext = this;

        Bugly.init(this, "5558e60e56", false);

        /*CrashReport.initCrashReport(getApplicationContext(), "5558e60e56", false);*/
        ToastUtils.init(mContext);

        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
       /* CrashReport.initCrashReport(getApplicationContext(), "3f548799f3", false);*/
       //临时用自己的账号
        CrashReport.initCrashReport(getApplicationContext(), "5558e60e56", false);

    }

    private void initMeituanLog() {


        LoganConfig config = new LoganConfig.Builder()
                .setCachePath(getApplicationContext().getFilesDir().getAbsolutePath())
                .setPath(getApplicationContext().getExternalFilesDir(null).getAbsolutePath()
                        + File.separator + "logan_v1")
                .setEncryptKey16("0123456789012345".getBytes())
                .setEncryptIV16("0123456789012345".getBytes())
                .build();
        Logan.init(config);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        Multidex.install(this);

        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);


        // 安装tinker
        Beta.installTinker();
    }

    /**
     * ��һ��View���ܱ����
     *
     * @param v
     */
    public static void disableView(final View v) {
        disableView(v, 3000);
    }
    /**
     * ��һ��View���ܱ����
     *
     * @param v
     * @param time
     */
    public static void disableView(final View v, int time) {
        if (false == v.isClickable()) {
            return;
        }
        v.setClickable(false);
        v.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setClickable(true);
                v.setEnabled(true);
            }
        }, time);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
    public static Context getBaseApplication(){

        //Log.d(TAG, "getBaseApplication: ");

        if (mContext!=null){
            return mContext;

        }
        return null;
    }

    static public void removeUser() {
        // secret = userid = mobile = null;
        store.remove("cookie_auth");
//        store.remove("mobile");
        store.remove("username");
        store.remove("organization_name");
        store.remove("organization_id");
        store.commit();
        App.store("gesture").clear().commit();
    }

    public static Store store(String name) {
        return Store.init(instance, name, Context.MODE_APPEND);
    }
    public static String formatStr(String str) {


        int len = strlen(str);

        if (len >= 10) {
            str = str.substring(0, 5);
            return str;
        }

        int padLen =10 - len;
        for (int i = 0; i < padLen; i++) {
            str += " ";
        }

        return str;


    }
    public static String formatRight2(String str) {


        if (str.contains("? ")){

            Log.d(TAG, "formatRight2: comntains ￥");


        }


        int len = strlen(str);
        //   str = str.replaceAll("\\.", "");

        if (len >= 20) {
//            str = str.substring(0,6);
            return str;
        }

        int padLen = 20 - len;
        for (int i = 0; i < padLen; i++) {
            str= " "+str;
        }

        return str;

    }
    public static String formatStr2(String str) {


        int len = strlen(str);

        if (len >= 7) {
            str = str.substring(0,7);
            return str;
        }

        int padLen =7 - len;
        for (int i = 0; i < padLen; i++) {
            str += " ";
        }

        return str;


    }
    public static int strlen(String str) {

        if (str == null) {
            return 0;
        }

        return str.replaceAll("[^\\x00-\\xff]", "**").length();
    }

}


