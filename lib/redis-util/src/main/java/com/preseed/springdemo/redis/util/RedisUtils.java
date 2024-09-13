package com.preseed.springdemo.redis.util;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisUtils {

	public RedisUtils(RedisTemplate redisTemplate){
		this.redisTemplate = redisTemplate;
	}
	
	 @Override
	 public String toString() {
	    JedisConnectionFactory conn = (JedisConnectionFactory)redisTemplate.getConnectionFactory();
		return "RedisUtil [redisTemplate=" +conn.getHostName()+ "]";
	}
	 
	private RedisTemplate<Object,Object> redisTemplate  = null;
	/**
	 * @Title: exits 普通类型
	 * : 是否存在
	 * @param @param key
	 */
	public  Boolean exist(String key){

		return redisTemplate.hasKey(key);
	}
	
	/**
	 * 删除缓存<br>
	 * 根据key精确匹配删除
	 * 
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public  void del(String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete((List) CollectionUtils.arrayToList(key));
			}
		}
	}

	/**
	 * 批量删除<br>
	 * （该操作会执行模糊查询，请尽量不要使用，以免影响性能或误删）
	 * 
	 * @param pattern
	 */
	public  void batchDel(String... pattern) {
		for (String kp : pattern) {
			redisTemplate.delete(redisTemplate.keys(kp + "*"));
		}
	}

	/**
	 * 取得缓存（int型）
	 * 
	 * @param key
	 * @return
	 */
	public  Integer getInt(String key) {
		Object value = redisTemplate.boundValueOps(key).get();//stringRedisTemplate.boundValueOps(key).get();
		if (null != value && !StringUtils.isEmpty(value.toString())) {
			return Integer.valueOf(value.toString());
		}
		return null;
	}

	/**
	 * 取得缓存（long型）
	 *
	 * @param key
	 * @return
	 */
	public Long getLong(String key) {
		Object value = redisTemplate.boundValueOps(key).get();//stringRedisTemplate.boundValueOps(key).get();
		if (null != value && !StringUtils.isEmpty(value.toString())) {
			return Long.valueOf(value.toString());
		}
		return null;
	}

	/**
	 * 取得缓存（字符串类型）
	 * 
	 * @param key
	 * @return
	public static String getStr(String key, boolean isDelete) {
		String value = stringRedisTemplate.boundValueOps(key).get();
		if (retain) {
			redisTemplate.delete(key);
		}
		return value;
	}
	 */
	
	/**
	 * 获取缓存<br>
	 * 注：基本数据类型(Character除外)，请直接使用get(String key, Class<T> clazz)取值
	 * @param key
	 * @return
	 */
	public  Object getObj(String key) {
		return redisTemplate.boundValueOps(key).get();
	}

	/**
	 * 获取缓存<br>
	 * 注：java 8种基本类型的数据请直接使用get(String key, Class<T> clazz)取值
	 * 
	 * @param key
	 * @param isDelete 是否删除
	 * @return
	 */
	public  Object getObj(String key, boolean isDelete) {
		Object obj = redisTemplate.boundValueOps(key).get();
		if (isDelete) {
			redisTemplate.delete(key);
		}
		return obj;
	}

	/**
	 * 获取缓存<br>
	 * 注：该方法暂不支持Character数据类型
	 * @param key
	 *            key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  <T> T get(Object key) {//, Class<T> clazz
		if(key.getClass().equals(byte[].class)){
			//value为byte[]时，key传byte[]类型获取
			return (T) redisTemplate.execute((RedisConnection redisConnection) -> redisConnection.get((byte[])key));
		}
		return (T) redisTemplate.boundValueOps(key).get();
	}

	/**
	 * 将value对象写入缓存
	 *
	 * @param key
	 * @param value
	 * @param time  失效时间(秒)
	 */
	public void set(String key, Object value, Long time) {
		Class<?> clz = value.getClass();
		if (time == null || time <= 0) {
			if (clz.equals(String.class) || clz.equals(Double.class) || clz.equals(Float.class)
					|| clz.equals(Short.class) || clz.equals(Boolean.class)) {
				redisTemplate.opsForValue().set(key, value.toString());
			} else if (clz.equals(Map.class)) {
				redisTemplate.opsForHash().putAll(key, (Map<? extends Object, ? extends Object>) value);
			} else if (value.getClass().equals(byte[].class)) {
				redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
					redisConnection.set(key.getBytes(), (byte[])value);
					return null;
				});
			} else {
				redisTemplate.opsForValue().set(key, value);
			}
		} else {
			if (clz.equals(String.class) || clz.equals(Double.class) || clz.equals(Float.class)
					|| clz.equals(Short.class) || clz.equals(Boolean.class)) {
				redisTemplate.opsForValue().set(key, value.toString(), time, TimeUnit.SECONDS);
			} else if (clz.equals(Map.class)) {
				redisTemplate.opsForHash().putAll(key, (Map<? extends Object, ? extends Object>) value);
				// FIXME 有可能导致指令未被执行
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			} else if (value.getClass().equals(byte[].class)) {
				redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
					redisConnection.set(key.getBytes(), (byte[])value, Expiration.seconds(time), RedisStringCommands.SetOption.UPSERT);
					return null;
				});
			} else {
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			}
		}
	}

	/**
	 * 获取当前key的过期日期
	 * @param key
	 * @param unit
	 * @return
	 */
	public Long getExpire(String key, TimeUnit unit) {
		return redisTemplate.getExpire(key, unit);
	}

	/**
	 * 将value对象以JSON格式写入缓存
	 * 
	 * @param key
	 * @param value
	 * @param time
	 *            失效时间(秒)
	public static void setJson(String key, Object value, Long time) {
		stringRedisTemplate.opsForValue().set(key, JsonMapper.toJsonString(value));
		if (null != time && time > 0) {
			stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
		}
	} */

	/**
	 * 更新key对象field的值
	 * 
	 * @param key 缓存key
	 * @param field  缓存对象field
	 * @param value  缓存对象field值
	public static void setJsonField(String key, String field, String value) {
		JSONObject obj = JsonMapper..parseObject(stringRedisTemplate.boundValueOps(key).get());
		obj.put(field, value);
		stringRedisTemplate.opsForValue().set(key, obj.toJSONString());
	}
	 */
	
	/**
	 * 递减操作
	 */
	public  double decr(String key, double by) {
		return redisTemplate.opsForValue().increment(key, -by);
	}

	/**
	 * 递增操作
	 */
	public  double incr(String key, double by) {
		return redisTemplate.opsForValue().increment(key, by);
	}
	
	/**
	 * 递减操作
	 */
	public  long decrLong(String key, long by) {
		return redisTemplate.opsForValue().increment(key, -by);
	}

	/**
	 * 递增操作
	 */
	public  long incrLong(String key, long by) {
		return redisTemplate.opsForValue().increment(key, by);

	}

	/**
	 * 递减操作
	 */
	public  long decrLong(String key, long by, Long time) {
		Long value = redisTemplate.opsForValue().increment(key, -by);
		if (time != null && time > 0) {
			redisTemplate.expire(key, time, TimeUnit.SECONDS);
		}
		return value;
	}

	/**
	 * 递增操作
	 */
	public  long incrLong(String key, long by, Long time) {
		Long value = redisTemplate.opsForValue().increment(key, by);
		if (time != null && time > 0) {
			redisTemplate.expire(key, time, TimeUnit.SECONDS);
		}
		return value;
	}

	/**
	 * 将map写入缓存
	 * @param key
	 * @param map
	 */
	public  <T> void setMap(String key, Map<String, T> map) {
		redisTemplate.opsForHash().putAll(key, map);
	}
	
	public  <T> void setMap(String key, Map<String, T> map,Long timeout) {
		setMap(key, map);
		if (null != timeout && timeout > 0) {
			redisTemplate.expire(key, timeout , TimeUnit.SECONDS);
		}
	}
	
	public  <T> void putIfAbsentMap(String key, String hashKey, T hashValue, Long timeout) {
		//setMap(key, map);
		addIfAbsentMap(key, hashKey, hashValue);
		if (null != timeout && timeout > 0) {
			redisTemplate.expire(key, timeout , TimeUnit.SECONDS);
		}
	}


	/**
	 * 向key对应的map中添加缓存对象
	 * 
	 * @param key cache对象key
	 * @param field  map对应的key
	 * @param value 值
	 */
	public  void addMap(String key, String field, String value) {
		redisTemplate.opsForHash().put(key, field, value);
	}

	/**
	 * 向key对应的map中添加缓存对象
	 * 
	 * @param key cache对象key
	 * @param field map对应的key
	 * @param obj  对象
	 */
	public  <T> void addMap(String key, String field, T obj) {
		redisTemplate.opsForHash().put(key, field, obj);
	}

	public  <T> void addIfAbsentMap(String key, String field, T obj) {
		redisTemplate.opsForHash().putIfAbsent(key, field, obj);
	}

	/**
	 * 获取map缓存
	 * @param key
	 */
	public  Map<String, Object> getMapObj(String key) {
		BoundHashOperations<Object, String, Object> boundHashOperations = redisTemplate.boundHashOps(key);
		return boundHashOperations.entries();
	}


	/**
	 * Map的 递减操作
	 */
	public  long decrLong4Map(String key,   String hashKey, long by) {
		return redisTemplate.opsForHash().increment(key, hashKey, -by);
	}

	/**
	 * Map的 递增操作
	 */
	public  long incrLong4Map(String key, String hashKey,  long by) {
		return redisTemplate.opsForHash().increment(key, hashKey, by);

	}
	
	/**
	 * @Title: hasKeyInMap
	 * : 是否存在
	 * @param @param key redisKey
	 * @param @param hashKey Map中的key
	 */
	public  Boolean hasKeyInMap(String key, String hashKey) {
		return redisTemplate.opsForHash().hasKey(key, hashKey);
	}
	
	/**
	 * @Title: getValueInMap
	 * : 获取rediskey 的map的key对应的值
	 */
	public  Object getValueInMap(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}
	/** 
	* @className RedisUtil
	*  获取当前key的hashMap
	* @params [key]
	* @return java.lang.Object
	* @author zhanyu.gu
	* @date 2020/5/19
	* @time 17:17
	*/
	public  Object getMapValue(String key) {
		return redisTemplate.opsForHash().values(key);
	}

    /**
     * @Title: multiGetValueInMap
     * : 获取rediskey 的map的keys对应的值列表
     */
    public  List multiGetValueInMap(String key, List hashKeys) {
        return redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

	/**
	 * 获取map缓存中的某个对象
	 * 
	 * @param key
	 * @param field
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  <T> T getMapField(String key, String field, Class<T> clazz) {
		return (T) redisTemplate.boundHashOps(key).get(field);
	}

	/**
	 * 删除map中的某个对象
	 * 
	 * @author lh
	 * @date 2016年8月10日
	 * @param key
	 *            map对应的key
	 * @param fields
	 *            map中该对象的key
	 */
	public void delMapField(String key, Object... fields) {
		redisTemplate.opsForHash().delete(key, fields);
	}

	/**
	 * 指定缓存的失效时间
	 * 
	 * @author FangJun
	 * @date 2016年8月14日
	 * @param key
	 *            缓存KEY
	 * @param time
	 *            失效时间(秒)
	 */
	public  void expire(String key, Long time) {
		if (null != time && time > 0) {
			redisTemplate.expire(key, time, TimeUnit.SECONDS);
		}
	}

	/**
	 * 添加set
	 * 
	 * @param key
	 * @param value
	 */
	public <T> void sadd(String key, T... value) {
		redisTemplate.boundSetOps(key).add(value);
	}

	/**
	 * 删除set集合中的对象
	 * 
	 * @param key
	 * @param value
	 */
	public <T> void srem(String key, T... value) {
		redisTemplate.boundSetOps(key).remove(value);
	}

	/**
	 * 获取set
	 *
	 * @param key
	 */
	public <T> Set<T> sget(String key) {
		return (Set<T>)redisTemplate.boundSetOps(key).members();
	}

	/**
	 * set重命名
	 * 
	 * @param oldkey
	 * @param newkey
	 */
	public  void srename(String oldkey, String newkey) {
		redisTemplate.boundSetOps(oldkey).rename(newkey);
	}

	/**
	 * 短信缓存
	 * 
	 * @author fxl
	 * @date 2016年9月11日
	 * @param key
	 * @param value
	 * @param time
	
	public static void setIntForPhone(String key, Object value, int time) {
		stringRedisTemplate.opsForValue().set(key, JsonMapper.toJsonString(value));
		if (time > 0) {
			stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
		}
	} */

	/**
	 * 模糊查询keys
	 * 
	 * @param pattern
	 * @return
	 */
	public  Set<Object> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}


	/**
	 * 批量写入
	 *
	 * @author xiaoyunlong
	 * @param map
	 * @return
	 */
	public void multiSet(Map<?, ?> map) {
		redisTemplate.opsForValue().multiSet(map);
	}

	/**
	 * 批量查询
	 *
	 * @author xiaoyunlong
	 * @param keys
	 * @return 查询结果的顺序与keys的顺序一直，无结果的为null
	 */
	public List<?> multiGet(List<?> keys) {
		return redisTemplate.opsForValue().multiGet((Collection)keys);
	}

	/**
	 * key 不存在时，为 key 设置指定的值
	 * key 存在时，不覆盖更新
	 *
	 * @author xiaoyunlong
	 * @date 2019/12/13
	 * @param time 失效时间（秒）
	 * @return 设置成功，返回 true；设置失败，返回 false
	 */
	public boolean setNX(String key, String value, Long time) {
		boolean result = redisTemplate.opsForValue().setIfAbsent(key,value);
		if(result && null != time && time > 0) {
			redisTemplate.expire(key, time, TimeUnit.SECONDS);
		}
		return result;
	}

	public boolean setNX(String key, Object value) {
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

}
