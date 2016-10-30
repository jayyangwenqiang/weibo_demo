package com.example.myweibo.domain;

import java.util.List;

public class Trends {
	private int showapi_res_code;
	private String showapi_res_error;
	private ShowapiResBodyBean showapi_res_body;

	public int getShowapi_res_code() {
		return showapi_res_code;
	}

	public void setShowapi_res_code(int showapi_res_code) {
		this.showapi_res_code = showapi_res_code;
	}

	public String getShowapi_res_error() {
		return showapi_res_error;
	}

	public void setShowapi_res_error(String showapi_res_error) {
		this.showapi_res_error = showapi_res_error;
	}

	public ShowapiResBodyBean getShowapi_res_body() {
		return showapi_res_body;
	}

	public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
		this.showapi_res_body = showapi_res_body;
	}

	public static class ShowapiResBodyBean {
		/**
		 * id : 1 title : 曝赵薇豪宅内外景 url :
		 * http://www.baidu.com/baidu?cl=3&tn=SE_baiduhomet8_jmjb7mjw
		 * &fr=top1000&wd=%C6%D8%D5%D4%DE%B1%BA%C0%D5%AC%C4%DA%CD%E2%BE%B0
		 */

		private List<ListBean> list;

		public List<ListBean> getList() {
			return list;
		}

		public void setList(List<ListBean> list) {
			this.list = list;
		}

		public static class ListBean {
			private String id;
			private String title;
			private String url;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}
		}
	}
}
