package com.zhixin.zhfz.common.entity;

import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlPageEntity {

	private static final Logger logger = LoggerFactory.getLogger(SqlPageEntity.class);

	private boolean goodPage = false;

	private int page = 1;

	private int rows = 0;

	public SqlPageEntity(Object parameterObject, String sql) {
		init(parameterObject, sql);
	}

	public SqlPageEntity(BoundSql boundSql) {
		if (boundSql != null) {
			init(boundSql.getParameterObject(), boundSql.getSql());
		}
	}

	private void init(Object parameterObject, String sql) {
		// logger.info("init:"+parameterObject);
		// logger.info("init:"+parameterObject.getClass());
		// logger.info("init:"+sql);
		// logger.info("init:"+isGoodSql(sql));
		if (isGoodSql(sql) && parameterObject != null
				&& parameterObject instanceof Map) {
			Map map = (Map) parameterObject;
			Object xpage_ = map.get("xpage");
			Object xrows_ = map.get("xrows");

			Object page_ = map.get("page");
			Object rows_ = map.get("rows");
			//有显示使用 xload 的参数 xpage xrows
			if (xpage_ != null && xrows_ != null) {
				if (xpage_ instanceof Integer && xrows_ instanceof Integer) {
					this.page = (Integer) xpage_;
					this.rows = (Integer) xrows_;
					if (this.page >= 0 && this.rows > 0) {
						this.goodPage = true;
					}
				} else {
					try {
						this.page = Integer.valueOf(xpage_.toString());
						this.rows = Integer.valueOf(xrows_.toString());
						if (this.page >= 0 && this.rows > 0) {
							this.goodPage = true;
						}
					} catch (Exception e) {
						logger.error("", e);
					}

				}
			}else if (page_ != null && rows_ != null) {
				if (page_ instanceof Integer && rows_ instanceof Integer) {
					this.page = (Integer) page_;
					this.rows = (Integer) rows_;
					if (this.page >= 0 && this.rows > 0) {
						this.goodPage = true;
					}
				} else {
					try {
						this.page = Integer.valueOf(page_.toString());
						this.rows = Integer.valueOf(rows_.toString());
						if (this.page >= 0 && this.rows > 0) {
							this.goodPage = true;
						}
					} catch (Exception e) {
						logger.error("", e);
					}

				}
			}
			Object usePage_ = map.get("usePage");
			if (usePage_ == null) {
				this.goodPage = false;
			} else {
				if (usePage_ instanceof Boolean) {
					Boolean up = (Boolean) usePage_;
					if (!up) {
						this.goodPage = false;
					}
				}
			}
		}
	}

	private boolean isGoodSql(String sql) {

		if (sql == null || !sql.toLowerCase().trim().startsWith("select")) {
			return false;
		} else {
			int k = sql.toLowerCase().indexOf("count");
			if (k < 15 && k > 0) {
				return false;
			} else {
				return true;
			}
		}

	}

	@Override
	public String toString() {
		return "SqlPage [goodPage=" + goodPage + ", page=" + page + ", rows="
				+ rows + "]";
	}

	public int getStart() {
		if ((page - 1) * rows < 0) {
			return 0;
		} else {
			return (page - 1) * rows;
		}
	}

	/**
	 * @return the goodPage
	 */
	public boolean isGoodPage() {
		return goodPage;
	}

	/**
	 * @param goodPage
	 *            the goodPage to set
	 */
	public void setGoodPage(boolean goodPage) {
		this.goodPage = goodPage;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

}
