package cgrass.print.printprovider.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * @describe sharepreference  数据存储，包括对象
 * @author cq
 *
 */
public class SharePreferenceUtils {
	private SharedPreferences mKV;
	private Editor mEditor;
	private static SharePreferenceUtils instance;
	private Context context;
	public String ERROR_INFO = null;
	private static final String TAG = "SharePreferenceUtils";

	/**
	 * 构造方法。
	 * 
	 * @param context
	 * @param kvName
	 *            键值表名称。
	 * @param 	 *            打开的模式。值为Context.MODE_APPEND, Context.MODE_PRIVATE,
	 *            Context.WORLD_READABLE, Context.WORLD_WRITEABLE.
	 */
	public SharePreferenceUtils(Context context, String kvName) {
		this.context = context;
		mKV = context.getSharedPreferences(kvName, Context.MODE_PRIVATE);
		mEditor = mKV.edit();
	}

	/**
	 * 获取保存着的boolean对象。
	 * 
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public boolean getBoolean(String key, boolean defValue) {
		return mKV.getBoolean(key, defValue);
	}

	/**
	 * 获取保存着的int对象。
	 * 
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public int getInt(String key, int defValue) {
		return mKV.getInt(key, defValue);
	}

	/**
	 * 获取保存着的long对象。
	 * 
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public long getLong(String key, long defValue) {
		return mKV.getLong(key, defValue);
	}

	/**
	 * 获取保存着的float对象。
	 * 
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public float getFloat(String key, float defValue) {
		return mKV.getFloat(key, defValue);
	}

	/**
	 * 获取保存着的String对象。
	 * 
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public String getString(String key, String defValue) {
		return mKV.getString(key, defValue);
	}

	/**
	 * 获取所有键值对。
	 * 
	 * @return 获取到的所胡键值对。
	 */
	public Map<String, ?> getAll() {
		return mKV.getAll();
	}

	/**
	 * 设置一个键值对，它将在{@linkplain #commit()}被调用时保存。<br/>
	 * 注意：当保存的value不是boolean, byte(会被转换成int保存),int, long, float,
	 * String等类型时将调用它的toString()方法进行值的保存。
	 * 
	 * @param key
	 *            键名称。
	 * @param value
	 *            值。
	 * @return 引用的KV对象。
	 */
	public SharePreferenceUtils put(String key, Object value) {
		if (value instanceof Boolean) {
			mEditor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Integer || value instanceof Byte) {
			mEditor.putInt(key, (Integer) value);
		} else if (value instanceof Long) {
			mEditor.putLong(key, (Long) value);
		} else if (value instanceof Float) {
			mEditor.putFloat(key, (Float) value);
		} else if (value instanceof String) {
			mEditor.putString(key, (String) value);
		} else {
			mEditor.putString(key, value.toString());
		}
		return this;
	}

	/**
	 * SharedPreferences保存对象-----
	 * 
	 * @param
	 * @param key
	 * @param object
	 */

	@SuppressLint("NewApi")
	public void setObjectValue(String key, Object object) {
		String objectBase64 = "";
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			objectBase64 = Base64.encodeToString(baos.toByteArray(),
					Base64.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mEditor.putString(key, objectBase64);
		mEditor.commit();
	}

	/**
	 * SharedPreferences取得对象
	 * 
	 * @param
	 * @param key
	 * @return
	 */
	@SuppressLint("NewApi")
	public Object getObjectValue(String key) {
		Object object = null;
		try {
			String objectBase64 = mKV.getString(key, "");
			byte[] base64Bytes = Base64.decode(objectBase64.getBytes(),
					Base64.DEFAULT);
			ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			object = ois.readObject();
		} catch (Exception e) {// 发生异常情况下清空对应缓存
			ERROR_INFO = e.toString();
			Log.e(TAG, e.toString());
		}
		return object;
	}

	/**
	 * 移除键值对。
	 * 
	 * @param key
	 *            要移除的键名称。
	 * @return 引用的KV对象。
	 */
	public SharePreferenceUtils remove(String key) {
		mEditor.remove(key);
		return this;
	}

	/**
	 * 清除所有键值对。
	 * 
	 * @return 引用的KV对象。
	 */
	public SharePreferenceUtils clear() {
		mEditor.clear();
		return this;
	}

	/**
	 * 是否包含某个键。
	 * 
	 * @param key
	 *            查询的键名称。
	 * @return 当且仅当包含该键时返回true, 否则返回false.
	 */
	public boolean contains(String key) {
		return mKV.contains(key);
	}

	/**
	 * 返回是否提交成功。
	 * 
	 * @return 当且仅当提交成功时返回true, 否则返回false.
	 */
	public boolean commit() {
		return mEditor.commit();
	}

}
