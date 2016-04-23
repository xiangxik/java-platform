package com.whenling.centralize;

/**
 * 扩展
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:33:33
 */
public abstract class Extension {

	public abstract void init(Application app, boolean isNew, boolean isUpdate, Integer historyVersion);

	public abstract Integer getVersion();

	public abstract String getAuthor();

	public String getName() {
		return getClass().getSimpleName();
	};

	public String getCode() {
		return getClass().getName();
	}

}
