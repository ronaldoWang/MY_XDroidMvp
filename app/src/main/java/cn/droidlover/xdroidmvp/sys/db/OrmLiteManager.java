package cn.droidlover.xdroidmvp.sys.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.SQLiteHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import cn.droidlover.xdroidmvp.sys.App;
import cn.droidlover.xdroidmvp.sys.R;

/**
 * ormlite数据库管理类
 */
public class OrmLiteManager {
    public static final String DB_NAME = "pda.db";
    public static final String PACKAGE_NAME = App.getContext().getPackageName();
    public static final String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME + "/databases/";

    private static LiteOrm liteOrm;
    private static OrmLiteManager instance;

    public OrmLiteManager(Context context) {
        File dbDir = new File(DB_PATH);
        File[] dbFiles = dbDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                if (s.contains("pda") && !s.equals(DB_NAME) && !s.equals(DB_NAME + "-journal")) {
                    return true;
                }
                return false;
            }
        });
        if (null != dbFiles) {
            for (File dbfile : dbFiles) {
                dbfile.delete();
            }
        }
        copyRawDB(context);
    }

    private void copyRawDB(Context context) {
        try {
            String databaseFilename = DB_PATH + DB_NAME;
            File dir = new File(DB_PATH);

            if (!dir.exists()) {
                dir.mkdir();
            }
            File dataFile = new File(databaseFilename);
            if (!dataFile.exists()) {
                InputStream is = context.getResources().openRawResource(
                        R.raw.pda);
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[1024];
                int count = 0;

                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }

                fos.close();
                is.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static OrmLiteManager getInstance(Context context) {
        if (instance == null) {
            instance = new OrmLiteManager(context);
        }
        return instance;
    }

    public LiteOrm getLiteOrm(Context context) {
        if (liteOrm == null) {
            DataBaseConfig config = new DataBaseConfig(context, DB_PATH + DB_NAME);
            config.debugged = true; // open the log
            config.dbVersion = 1; // set database version
            // set database update listener
            config.onUpdateListener = new SQLiteHelper.OnUpdateListener() {
                @Override
                public void onUpdate(SQLiteDatabase var1, int oldCode, int newCode) {

                }

            };
            liteOrm = LiteOrm.newCascadeInstance(config);
        }
        return liteOrm;
    }

    /**
     * 查询集合
     *
     * @param context
     * @param clazz
     * @param distinct
     * @param where
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @param <T>
     * @return
     */
    public static <T> List<T> query(Context context, Class<T> clazz, boolean distinct, String where,
                                    String groupBy, String having, String orderBy, String limit) {
        LiteOrm liteOrm = OrmLiteManager.getInstance(context).getLiteOrm(context);
        QueryBuilder<T> queryBuilder = new QueryBuilder<T>(clazz);
        queryBuilder.distinct(distinct);
        queryBuilder.where(where);
        queryBuilder.groupBy(groupBy);
        queryBuilder.having(having);
        queryBuilder.orderBy(orderBy);
        queryBuilder.limit(limit);
        return liteOrm.query(queryBuilder);
    }

    /**
     * 获得总数
     *
     * @param context
     * @param clazz
     * @param where
     * @return
     */
    public static <T> Long getCount(Context context, Class<T> clazz, String where) {
        LiteOrm liteOrm = OrmLiteManager.getInstance(context).getLiteOrm(context);
        QueryBuilder<T> queryBuilder = new QueryBuilder<T>(clazz);
        queryBuilder.where(where);
        return liteOrm.queryCount(queryBuilder);
    }

    /**
     * 保存
     *
     * @param context
     * @param entity
     * @return
     */
    public static Long save(Context context, Objects entity) {
        try {
            LiteOrm liteOrm = OrmLiteManager.getInstance(context).getLiteOrm(context);
            Class clazz = entity.getClass();
            Field id = clazz.getDeclaredField("id");
            if (null == id.get(entity)) {
                liteOrm.insert(entity);
            } else {
                liteOrm.update(entity);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    public static boolean delete(Context context, Objects entity) {
        LiteOrm liteOrm = OrmLiteManager.getInstance(context).getLiteOrm(context);
        int del = liteOrm.delete(entity);
        if (del > 0) {
            return true;
        } else {
            return false;
        }
    }
}
