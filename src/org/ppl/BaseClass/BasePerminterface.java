package org.ppl.BaseClass;

public interface BasePerminterface {
	/**
	 * @since manager acl read perminter
	 * @param arg
	 */
	public void read(Object arg);
	/**
	 * @since manager acl create perminter
	 * @param arg
	 */
	public void create(Object arg);
	/**
	 * @since manager acl edit perminter
	 * @param arg
	 */
	public void edit(Object arg);
	/**
	 * @since manager acl remove perminter
	 * @param arg
	 */
	public void remove(Object arg);
	/**
	 * @since manager acl search perminter
	 * @param arg
	 */
	public void search(Object arg);
}
