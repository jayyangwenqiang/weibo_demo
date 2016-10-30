package com.example.myweibo.domain;

import java.io.Serializable;
import java.util.List;

public class WeiboBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean hasvisible;
	private long previous_cursor;
	private long next_cursor;
	private int total_number;
	private int interval;
	private int uve_blank;
	private long since_id;
	private long max_id;
	private int has_unread;

	private List<StatusesBean> statuses;

	public boolean isHasvisible() {
		return hasvisible;
	}

	public void setHasvisible(boolean hasvisible) {
		this.hasvisible = hasvisible;
	}

	public long getPrevious_cursor() {
		return previous_cursor;
	}

	public void setPrevious_cursor(long previous_cursor) {
		this.previous_cursor = previous_cursor;
	}

	public long getNext_cursor() {
		return next_cursor;
	}

	public void setNext_cursor(long next_cursor) {
		this.next_cursor = next_cursor;
	}

	public int getTotal_number() {
		return total_number;
	}

	public void setTotal_number(int total_number) {
		this.total_number = total_number;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getUve_blank() {
		return uve_blank;
	}

	public void setUve_blank(int uve_blank) {
		this.uve_blank = uve_blank;
	}

	public long getSince_id() {
		return since_id;
	}

	public void setSince_id(long since_id) {
		this.since_id = since_id;
	}

	public long getMax_id() {
		return max_id;
	}

	public void setMax_id(long max_id) {
		this.max_id = max_id;
	}

	public int getHas_unread() {
		return has_unread;
	}

	public void setHas_unread(int has_unread) {
		this.has_unread = has_unread;
	}

	public List<StatusesBean> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<StatusesBean> statuses) {
		this.statuses = statuses;
	}

	public static class StatusesBean implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 12L;
		private String created_at;
		private long id;
		private String mid;
		private String idstr;
		private String text;
		private int textLength;
		private int source_allowclick;
		private int source_type;
		private String source;
		private boolean favorited;
		private boolean truncated;
		private String in_reply_to_status_id;
		private String in_reply_to_user_id;
		private String in_reply_to_screen_name;
		private String thumbnail_pic;
		private String bmiddle_pic;
		private String original_pic;
		private Object geo;
		/**
		 * id : 1715625603 idstr : 1715625603 class : 1 screen_name : 木皮叔 name :
		 * 木皮叔 province : 22 city : 2 location : 吉林 吉林 description :
		 * 我热爱这个世界，只好拼命感知！ url : http://blog.sina.com/jianbaxin
		 * profile_image_url :
		 * http://tva3.sinaimg.cn/crop.0.0.749.749.50/66425e83
		 * jw8f37hieuqxlj20ku0kt3zf.jpg cover_image_phone :
		 * http://ww1.sinaimg.cn
		 * /crop.0.0.640.640.640/9d44112bjw1f1xl1c10tuj20hs0hs0tw.jpg
		 * profile_url : 206661960 domain : jianbaxin weihao : 206661960 gender
		 * : m followers_count : 105009 friends_count : 180 pagefriends_count :
		 * 7 statuses_count : 2089 favourites_count : 31 created_at : Sun Mar 21
		 * 14:53:33 +0800 2010 following : true allow_all_act_msg : false
		 * geo_enabled : true verified : true verified_type : 0 remark : ptype :
		 * 0 allow_all_comment : true avatar_large :
		 * http://tva3.sinaimg.cn/crop.0
		 * .0.749.749.180/66425e83jw8f37hieuqxlj20ku0kt3zf.jpg avatar_hd :
		 * http://tva3.sinaimg.cn/crop.0.0.749.749.1024/66425e83
		 * jw8f37hieuqxlj20ku0kt3zf.jpg verified_reason : 微博读书推荐作者，简书推荐作者
		 * verified_trade : 1115 verified_reason_url : verified_source :
		 * verified_source_url : verified_state : 0 verified_level : 3
		 * verified_type_ext : 0 verified_reason_modified :
		 * verified_contact_name : verified_contact_email :
		 * verified_contact_mobile : follow_me : false online_status : 0
		 * bi_followers_count : 97 lang : zh-cn star : 0 mbtype : 11 mbrank : 2
		 * block_word : 0 block_app : 1 credit_score : 80 user_ability : 520
		 * cardid : star_001 avatargj_id : gj_vip_011 urank : 24
		 */
		private RetweetedStatusBean retweeted_status;

		public RetweetedStatusBean getRetweeted_status() {
			return retweeted_status;
		}

		public void setRetweeted_status(RetweetedStatusBean retweeted_status) {
			this.retweeted_status = retweeted_status;
		}

		private UserBean user;
		private int reposts_count;
		private int comments_count;
		private int attitudes_count;
		private boolean isLongText;
		private int mlevel;
		/**
		 * type : 0 list_id : 0
		 */

		private VisibleBean visible;
		private long biz_feature;
		private int hasActionTypeCard;
		private String rid;
		private int userType;
		private String cardid;
		private int positive_recom_flag;
		private String gif_ids;
		private int is_show_bulletin;
		/**
		 * thumbnail_pic :
		 * http://ww4.sinaimg.cn/thumbnail/66425e83jw1f90tak72ogj20ia0rfdib.jpg
		 */

		private List<PicUrlsBean> pic_urls;
		/**
		 * client_mblogid : iPhone-59AF7E2A-1BC3-4707-BD15-36D1DF19751B
		 */

		private List<AnnotationsBean> annotations;
		private List<?> darwin_tags;
		private List<?> hot_weibo_tags;
		private List<?> text_tag_tips;

		public static class RetweetedStatusBean implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = 21L;
			private String created_at;
			private long id;
			private String mid;
			private String idstr;
			private String text;
			private int textLength;
			private int source_allowclick;
			private int source_type;
			private String source;
			private boolean favorited;
			private boolean truncated;
			private String in_reply_to_status_id;
			private String in_reply_to_user_id;
			private String in_reply_to_screen_name;
			private String thumbnail_pic;
			private String bmiddle_pic;
			private String original_pic;
			private Object geo;
			/**
			 * id : 1715625603 idstr : 1715625603 class : 1 screen_name : 木皮叔
			 * name : 木皮叔 province : 22 city : 2 location : 吉林 吉林 description :
			 * 我热爱这个世界，只好拼命感知！ url : http://blog.sina.com/jianbaxin
			 * profile_image_url :
			 * http://tva3.sinaimg.cn/crop.0.0.749.749.50/66425e83
			 * jw8f37hieuqxlj20ku0kt3zf.jpg cover_image_phone :
			 * http://ww1.sinaimg
			 * .cn/crop.0.0.640.640.640/9d44112bjw1f1xl1c10tuj20hs0hs0tw.jpg
			 * profile_url : 206661960 domain : jianbaxin weihao : 206661960
			 * gender : m followers_count : 105009 friends_count : 180
			 * pagefriends_count : 7 statuses_count : 2089 favourites_count : 31
			 * created_at : Sun Mar 21 14:53:33 +0800 2010 following : true
			 * allow_all_act_msg : false geo_enabled : true verified : true
			 * verified_type : 0 remark : ptype : 0 allow_all_comment : true
			 * avatar_large :
			 * http://tva3.sinaimg.cn/crop.0.0.749.749.180/66425e83
			 * jw8f37hieuqxlj20ku0kt3zf.jpg avatar_hd :
			 * http://tva3.sinaimg.cn/crop
			 * .0.0.749.749.1024/66425e83jw8f37hieuqxlj20ku0kt3zf.jpg
			 * verified_reason : 微博读书推荐作者，简书推荐作者 verified_trade : 1115
			 * verified_reason_url : verified_source : verified_source_url :
			 * verified_state : 0 verified_level : 3 verified_type_ext : 0
			 * verified_reason_modified : verified_contact_name :
			 * verified_contact_email : verified_contact_mobile : follow_me :
			 * false online_status : 0 bi_followers_count : 97 lang : zh-cn star
			 * : 0 mbtype : 11 mbrank : 2 block_word : 0 block_app : 1
			 * credit_score : 80 user_ability : 520 cardid : star_001
			 * avatargj_id : gj_vip_011 urank : 24
			 */

			private UserBean user;
			private int reposts_count;
			private int comments_count;
			private int attitudes_count;
			private boolean isLongText;
			private int mlevel;
			/**
			 * type : 0 list_id : 0
			 */

			private VisibleBean visible;
			private int hasActionTypeCard;
			private int userType;
			private String cardid;
			private int positive_recom_flag;
			private String gif_ids;
			private int is_show_bulletin;
			/**
			 * thumbnail_pic : http://ww1.sinaimg.cn/thumbnail/66425e83
			 * jw1f91xbf1jnxj20cx0jgn1b.jpg
			 */

			private List<PicUrlsBean> pic_urls;
			/**
			 * client_mblogid : iPhone-340A96C9-89FA-48F6-BA46-520128EB149C
			 */

			private List<AnnotationsBean> annotations;
			private List<?> darwin_tags;
			private List<?> hot_weibo_tags;
			private List<?> text_tag_tips;

			public String getCreated_at() {
				return created_at;
			}

			public void setCreated_at(String created_at) {
				this.created_at = created_at;
			}

			public long getId() {
				return id;
			}

			public void setId(long id) {
				this.id = id;
			}

			public String getMid() {
				return mid;
			}

			public void setMid(String mid) {
				this.mid = mid;
			}

			public String getIdstr() {
				return idstr;
			}

			public void setIdstr(String idstr) {
				this.idstr = idstr;
			}

			public String getText() {
				return text;
			}

			public void setText(String text) {
				this.text = text;
			}

			public int getTextLength() {
				return textLength;
			}

			public void setTextLength(int textLength) {
				this.textLength = textLength;
			}

			public int getSource_allowclick() {
				return source_allowclick;
			}

			public void setSource_allowclick(int source_allowclick) {
				this.source_allowclick = source_allowclick;
			}

			public int getSource_type() {
				return source_type;
			}

			public void setSource_type(int source_type) {
				this.source_type = source_type;
			}

			public String getSource() {
				return source;
			}

			public void setSource(String source) {
				this.source = source;
			}

			public boolean isFavorited() {
				return favorited;
			}

			public void setFavorited(boolean favorited) {
				this.favorited = favorited;
			}

			public boolean isTruncated() {
				return truncated;
			}

			public void setTruncated(boolean truncated) {
				this.truncated = truncated;
			}

			public String getIn_reply_to_status_id() {
				return in_reply_to_status_id;
			}

			public void setIn_reply_to_status_id(String in_reply_to_status_id) {
				this.in_reply_to_status_id = in_reply_to_status_id;
			}

			public String getIn_reply_to_user_id() {
				return in_reply_to_user_id;
			}

			public void setIn_reply_to_user_id(String in_reply_to_user_id) {
				this.in_reply_to_user_id = in_reply_to_user_id;
			}

			public String getIn_reply_to_screen_name() {
				return in_reply_to_screen_name;
			}

			public void setIn_reply_to_screen_name(
					String in_reply_to_screen_name) {
				this.in_reply_to_screen_name = in_reply_to_screen_name;
			}

			public String getThumbnail_pic() {
				return thumbnail_pic;
			}

			public void setThumbnail_pic(String thumbnail_pic) {
				this.thumbnail_pic = thumbnail_pic;
			}

			public String getBmiddle_pic() {
				return bmiddle_pic;
			}

			public void setBmiddle_pic(String bmiddle_pic) {
				this.bmiddle_pic = bmiddle_pic;
			}

			public String getOriginal_pic() {
				return original_pic;
			}

			public void setOriginal_pic(String original_pic) {
				this.original_pic = original_pic;
			}

			public Object getGeo() {
				return geo;
			}

			public void setGeo(Object geo) {
				this.geo = geo;
			}

			public UserBean getUser() {
				return user;
			}

			public void setUser(UserBean user) {
				this.user = user;
			}

			public int getReposts_count() {
				return reposts_count;
			}

			public void setReposts_count(int reposts_count) {
				this.reposts_count = reposts_count;
			}

			public int getComments_count() {
				return comments_count;
			}

			public void setComments_count(int comments_count) {
				this.comments_count = comments_count;
			}

			public int getAttitudes_count() {
				return attitudes_count;
			}

			public void setAttitudes_count(int attitudes_count) {
				this.attitudes_count = attitudes_count;
			}

			public boolean isIsLongText() {
				return isLongText;
			}

			public void setIsLongText(boolean isLongText) {
				this.isLongText = isLongText;
			}

			public int getMlevel() {
				return mlevel;
			}

			public void setMlevel(int mlevel) {
				this.mlevel = mlevel;
			}

			public VisibleBean getVisible() {
				return visible;
			}

			public void setVisible(VisibleBean visible) {
				this.visible = visible;
			}

			public int getHasActionTypeCard() {
				return hasActionTypeCard;
			}

			public void setHasActionTypeCard(int hasActionTypeCard) {
				this.hasActionTypeCard = hasActionTypeCard;
			}

			public int getUserType() {
				return userType;
			}

			public void setUserType(int userType) {
				this.userType = userType;
			}

			public String getCardid() {
				return cardid;
			}

			public void setCardid(String cardid) {
				this.cardid = cardid;
			}

			public int getPositive_recom_flag() {
				return positive_recom_flag;
			}

			public void setPositive_recom_flag(int positive_recom_flag) {
				this.positive_recom_flag = positive_recom_flag;
			}

			public String getGif_ids() {
				return gif_ids;
			}

			public void setGif_ids(String gif_ids) {
				this.gif_ids = gif_ids;
			}

			public int getIs_show_bulletin() {
				return is_show_bulletin;
			}

			public void setIs_show_bulletin(int is_show_bulletin) {
				this.is_show_bulletin = is_show_bulletin;
			}

			public List<PicUrlsBean> getPic_urls() {
				return pic_urls;
			}

			public void setPic_urls(List<PicUrlsBean> pic_urls) {
				this.pic_urls = pic_urls;
			}

			public List<AnnotationsBean> getAnnotations() {
				return annotations;
			}

			public void setAnnotations(List<AnnotationsBean> annotations) {
				this.annotations = annotations;
			}

			public List<?> getDarwin_tags() {
				return darwin_tags;
			}

			public void setDarwin_tags(List<?> darwin_tags) {
				this.darwin_tags = darwin_tags;
			}

			public List<?> getHot_weibo_tags() {
				return hot_weibo_tags;
			}

			public void setHot_weibo_tags(List<?> hot_weibo_tags) {
				this.hot_weibo_tags = hot_weibo_tags;
			}

			public List<?> getText_tag_tips() {
				return text_tag_tips;
			}

			public void setText_tag_tips(List<?> text_tag_tips) {
				this.text_tag_tips = text_tag_tips;
			}

			public static class UserBean implements Serializable {
				/**
				 * 
				 */
				private static final long serialVersionUID = 22L;
				private long id;
				private String idstr;

				private String screen_name;
				private String name;
				private String province;
				private String city;
				private String location;
				private String description;
				private String url;
				private String profile_image_url;
				private String cover_image_phone;
				private String profile_url;
				private String domain;
				private String weihao;
				private String gender;
				private int followers_count;
				private int friends_count;
				private int pagefriends_count;
				private int statuses_count;
				private int favourites_count;
				private String created_at;
				private boolean following;
				private boolean allow_all_act_msg;
				private boolean geo_enabled;
				private boolean verified;
				private int verified_type;
				private String remark;
				private int ptype;
				private boolean allow_all_comment;
				private String avatar_large;
				private String avatar_hd;
				private String verified_reason;
				private String verified_trade;
				private String verified_reason_url;
				private String verified_source;
				private String verified_source_url;
				private int verified_state;
				private int verified_level;
				private int verified_type_ext;
				private String verified_reason_modified;
				private String verified_contact_name;
				private String verified_contact_email;
				private String verified_contact_mobile;
				private boolean follow_me;
				private int online_status;
				private int bi_followers_count;
				private String lang;
				private int star;
				private int mbtype;
				private int mbrank;
				private int block_word;
				private int block_app;
				private int credit_score;
				private int user_ability;
				private String cardid;
				private String avatargj_id;
				private int urank;

				public long getId() {
					return id;
				}

				public void setId(long id) {
					this.id = id;
				}

				public String getIdstr() {
					return idstr;
				}

				public void setIdstr(String idstr) {
					this.idstr = idstr;
				}

				public String getScreen_name() {
					return screen_name;
				}

				public void setScreen_name(String screen_name) {
					this.screen_name = screen_name;
				}

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public String getProvince() {
					return province;
				}

				public void setProvince(String province) {
					this.province = province;
				}

				public String getCity() {
					return city;
				}

				public void setCity(String city) {
					this.city = city;
				}

				public String getLocation() {
					return location;
				}

				public void setLocation(String location) {
					this.location = location;
				}

				public String getDescription() {
					return description;
				}

				public void setDescription(String description) {
					this.description = description;
				}

				public String getUrl() {
					return url;
				}

				public void setUrl(String url) {
					this.url = url;
				}

				public String getProfile_image_url() {
					return profile_image_url;
				}

				public void setProfile_image_url(String profile_image_url) {
					this.profile_image_url = profile_image_url;
				}

				public String getCover_image_phone() {
					return cover_image_phone;
				}

				public void setCover_image_phone(String cover_image_phone) {
					this.cover_image_phone = cover_image_phone;
				}

				public String getProfile_url() {
					return profile_url;
				}

				public void setProfile_url(String profile_url) {
					this.profile_url = profile_url;
				}

				public String getDomain() {
					return domain;
				}

				public void setDomain(String domain) {
					this.domain = domain;
				}

				public String getWeihao() {
					return weihao;
				}

				public void setWeihao(String weihao) {
					this.weihao = weihao;
				}

				public String getGender() {
					return gender;
				}

				public void setGender(String gender) {
					this.gender = gender;
				}

				public int getFollowers_count() {
					return followers_count;
				}

				public void setFollowers_count(int followers_count) {
					this.followers_count = followers_count;
				}

				public int getFriends_count() {
					return friends_count;
				}

				public void setFriends_count(int friends_count) {
					this.friends_count = friends_count;
				}

				public int getPagefriends_count() {
					return pagefriends_count;
				}

				public void setPagefriends_count(int pagefriends_count) {
					this.pagefriends_count = pagefriends_count;
				}

				public int getStatuses_count() {
					return statuses_count;
				}

				public void setStatuses_count(int statuses_count) {
					this.statuses_count = statuses_count;
				}

				public int getFavourites_count() {
					return favourites_count;
				}

				public void setFavourites_count(int favourites_count) {
					this.favourites_count = favourites_count;
				}

				public String getCreated_at() {
					return created_at;
				}

				public void setCreated_at(String created_at) {
					this.created_at = created_at;
				}

				public boolean isFollowing() {
					return following;
				}

				public void setFollowing(boolean following) {
					this.following = following;
				}

				public boolean isAllow_all_act_msg() {
					return allow_all_act_msg;
				}

				public void setAllow_all_act_msg(boolean allow_all_act_msg) {
					this.allow_all_act_msg = allow_all_act_msg;
				}

				public boolean isGeo_enabled() {
					return geo_enabled;
				}

				public void setGeo_enabled(boolean geo_enabled) {
					this.geo_enabled = geo_enabled;
				}

				public boolean isVerified() {
					return verified;
				}

				public void setVerified(boolean verified) {
					this.verified = verified;
				}

				public int getVerified_type() {
					return verified_type;
				}

				public void setVerified_type(int verified_type) {
					this.verified_type = verified_type;
				}

				public String getRemark() {
					return remark;
				}

				public void setRemark(String remark) {
					this.remark = remark;
				}

				public int getPtype() {
					return ptype;
				}

				public void setPtype(int ptype) {
					this.ptype = ptype;
				}

				public boolean isAllow_all_comment() {
					return allow_all_comment;
				}

				public void setAllow_all_comment(boolean allow_all_comment) {
					this.allow_all_comment = allow_all_comment;
				}

				public String getAvatar_large() {
					return avatar_large;
				}

				public void setAvatar_large(String avatar_large) {
					this.avatar_large = avatar_large;
				}

				public String getAvatar_hd() {
					return avatar_hd;
				}

				public void setAvatar_hd(String avatar_hd) {
					this.avatar_hd = avatar_hd;
				}

				public String getVerified_reason() {
					return verified_reason;
				}

				public void setVerified_reason(String verified_reason) {
					this.verified_reason = verified_reason;
				}

				public String getVerified_trade() {
					return verified_trade;
				}

				public void setVerified_trade(String verified_trade) {
					this.verified_trade = verified_trade;
				}

				public String getVerified_reason_url() {
					return verified_reason_url;
				}

				public void setVerified_reason_url(String verified_reason_url) {
					this.verified_reason_url = verified_reason_url;
				}

				public String getVerified_source() {
					return verified_source;
				}

				public void setVerified_source(String verified_source) {
					this.verified_source = verified_source;
				}

				public String getVerified_source_url() {
					return verified_source_url;
				}

				public void setVerified_source_url(String verified_source_url) {
					this.verified_source_url = verified_source_url;
				}

				public int getVerified_state() {
					return verified_state;
				}

				public void setVerified_state(int verified_state) {
					this.verified_state = verified_state;
				}

				public int getVerified_level() {
					return verified_level;
				}

				public void setVerified_level(int verified_level) {
					this.verified_level = verified_level;
				}

				public int getVerified_type_ext() {
					return verified_type_ext;
				}

				public void setVerified_type_ext(int verified_type_ext) {
					this.verified_type_ext = verified_type_ext;
				}

				public String getVerified_reason_modified() {
					return verified_reason_modified;
				}

				public void setVerified_reason_modified(
						String verified_reason_modified) {
					this.verified_reason_modified = verified_reason_modified;
				}

				public String getVerified_contact_name() {
					return verified_contact_name;
				}

				public void setVerified_contact_name(
						String verified_contact_name) {
					this.verified_contact_name = verified_contact_name;
				}

				public String getVerified_contact_email() {
					return verified_contact_email;
				}

				public void setVerified_contact_email(
						String verified_contact_email) {
					this.verified_contact_email = verified_contact_email;
				}

				public String getVerified_contact_mobile() {
					return verified_contact_mobile;
				}

				public void setVerified_contact_mobile(
						String verified_contact_mobile) {
					this.verified_contact_mobile = verified_contact_mobile;
				}

				public boolean isFollow_me() {
					return follow_me;
				}

				public void setFollow_me(boolean follow_me) {
					this.follow_me = follow_me;
				}

				public int getOnline_status() {
					return online_status;
				}

				public void setOnline_status(int online_status) {
					this.online_status = online_status;
				}

				public int getBi_followers_count() {
					return bi_followers_count;
				}

				public void setBi_followers_count(int bi_followers_count) {
					this.bi_followers_count = bi_followers_count;
				}

				public String getLang() {
					return lang;
				}

				public void setLang(String lang) {
					this.lang = lang;
				}

				public int getStar() {
					return star;
				}

				public void setStar(int star) {
					this.star = star;
				}

				public int getMbtype() {
					return mbtype;
				}

				public void setMbtype(int mbtype) {
					this.mbtype = mbtype;
				}

				public int getMbrank() {
					return mbrank;
				}

				public void setMbrank(int mbrank) {
					this.mbrank = mbrank;
				}

				public int getBlock_word() {
					return block_word;
				}

				public void setBlock_word(int block_word) {
					this.block_word = block_word;
				}

				public int getBlock_app() {
					return block_app;
				}

				public void setBlock_app(int block_app) {
					this.block_app = block_app;
				}

				public int getCredit_score() {
					return credit_score;
				}

				public void setCredit_score(int credit_score) {
					this.credit_score = credit_score;
				}

				public int getUser_ability() {
					return user_ability;
				}

				public void setUser_ability(int user_ability) {
					this.user_ability = user_ability;
				}

				public String getCardid() {
					return cardid;
				}

				public void setCardid(String cardid) {
					this.cardid = cardid;
				}

				public String getAvatargj_id() {
					return avatargj_id;
				}

				public void setAvatargj_id(String avatargj_id) {
					this.avatargj_id = avatargj_id;
				}

				public int getUrank() {
					return urank;
				}

				public void setUrank(int urank) {
					this.urank = urank;
				}
			}

			public static class VisibleBean implements Serializable {
				/**
				 * 
				 */
				private static final long serialVersionUID = 25L;
				private int type;
				private int list_id;

				public int getType() {
					return type;
				}

				public void setType(int type) {
					this.type = type;
				}

				public int getList_id() {
					return list_id;
				}

				public void setList_id(int list_id) {
					this.list_id = list_id;
				}
			}

			public static class PicUrlsBean implements Serializable {
				/**
				 * 
				 */
				private static final long serialVersionUID = 24L;
				private String thumbnail_pic;

				public String getThumbnail_pic() {
					return thumbnail_pic;
				}

				public void setThumbnail_pic(String thumbnail_pic) {
					this.thumbnail_pic = thumbnail_pic;
				}
			}

			public static class AnnotationsBean implements Serializable{
				/**
				 * 
				 */
				private static final long serialVersionUID = 23L;
				private String client_mblogid;

				public String getClient_mblogid() {
					return client_mblogid;
				}

				public void setClient_mblogid(String client_mblogid) {
					this.client_mblogid = client_mblogid;
				}
			}
		}

		public String getCreated_at() {
			return created_at;
		}

		public void setCreated_at(String created_at) {
			this.created_at = created_at;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getMid() {
			return mid;
		}

		public void setMid(String mid) {
			this.mid = mid;
		}

		public String getIdstr() {
			return idstr;
		}

		public void setIdstr(String idstr) {
			this.idstr = idstr;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public int getTextLength() {
			return textLength;
		}

		public void setTextLength(int textLength) {
			this.textLength = textLength;
		}

		public int getSource_allowclick() {
			return source_allowclick;
		}

		public void setSource_allowclick(int source_allowclick) {
			this.source_allowclick = source_allowclick;
		}

		public int getSource_type() {
			return source_type;
		}

		public void setSource_type(int source_type) {
			this.source_type = source_type;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public boolean isFavorited() {
			return favorited;
		}

		public void setFavorited(boolean favorited) {
			this.favorited = favorited;
		}

		public boolean isTruncated() {
			return truncated;
		}

		public void setTruncated(boolean truncated) {
			this.truncated = truncated;
		}

		public String getIn_reply_to_status_id() {
			return in_reply_to_status_id;
		}

		public void setIn_reply_to_status_id(String in_reply_to_status_id) {
			this.in_reply_to_status_id = in_reply_to_status_id;
		}

		public String getIn_reply_to_user_id() {
			return in_reply_to_user_id;
		}

		public void setIn_reply_to_user_id(String in_reply_to_user_id) {
			this.in_reply_to_user_id = in_reply_to_user_id;
		}

		public String getIn_reply_to_screen_name() {
			return in_reply_to_screen_name;
		}

		public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
			this.in_reply_to_screen_name = in_reply_to_screen_name;
		}

		public String getThumbnail_pic() {
			return thumbnail_pic;
		}

		public void setThumbnail_pic(String thumbnail_pic) {
			this.thumbnail_pic = thumbnail_pic;
		}

		public String getBmiddle_pic() {
			return bmiddle_pic;
		}

		public void setBmiddle_pic(String bmiddle_pic) {
			this.bmiddle_pic = bmiddle_pic;
		}

		public String getOriginal_pic() {
			return original_pic;
		}

		public void setOriginal_pic(String original_pic) {
			this.original_pic = original_pic;
		}

		public Object getGeo() {
			return geo;
		}

		public void setGeo(Object geo) {
			this.geo = geo;
		}

		public UserBean getUser() {
			return user;
		}

		public void setUser(UserBean user) {
			this.user = user;
		}

		public int getReposts_count() {
			return reposts_count;
		}

		public void setReposts_count(int reposts_count) {
			this.reposts_count = reposts_count;
		}

		public int getComments_count() {
			return comments_count;
		}

		public void setComments_count(int comments_count) {
			this.comments_count = comments_count;
		}

		public int getAttitudes_count() {
			return attitudes_count;
		}

		public void setAttitudes_count(int attitudes_count) {
			this.attitudes_count = attitudes_count;
		}

		public boolean isIsLongText() {
			return isLongText;
		}

		public void setIsLongText(boolean isLongText) {
			this.isLongText = isLongText;
		}

		public int getMlevel() {
			return mlevel;
		}

		public void setMlevel(int mlevel) {
			this.mlevel = mlevel;
		}

		public VisibleBean getVisible() {
			return visible;
		}

		public void setVisible(VisibleBean visible) {
			this.visible = visible;
		}

		public long getBiz_feature() {
			return biz_feature;
		}

		public void setBiz_feature(long biz_feature) {
			this.biz_feature = biz_feature;
		}

		public int getHasActionTypeCard() {
			return hasActionTypeCard;
		}

		public void setHasActionTypeCard(int hasActionTypeCard) {
			this.hasActionTypeCard = hasActionTypeCard;
		}

		public String getRid() {
			return rid;
		}

		public void setRid(String rid) {
			this.rid = rid;
		}

		public int getUserType() {
			return userType;
		}

		public void setUserType(int userType) {
			this.userType = userType;
		}

		public String getCardid() {
			return cardid;
		}

		public void setCardid(String cardid) {
			this.cardid = cardid;
		}

		public int getPositive_recom_flag() {
			return positive_recom_flag;
		}

		public void setPositive_recom_flag(int positive_recom_flag) {
			this.positive_recom_flag = positive_recom_flag;
		}

		public String getGif_ids() {
			return gif_ids;
		}

		public void setGif_ids(String gif_ids) {
			this.gif_ids = gif_ids;
		}

		public int getIs_show_bulletin() {
			return is_show_bulletin;
		}

		public void setIs_show_bulletin(int is_show_bulletin) {
			this.is_show_bulletin = is_show_bulletin;
		}

		public List<PicUrlsBean> getPic_urls() {
			return pic_urls;
		}

		public void setPic_urls(List<PicUrlsBean> pic_urls) {
			this.pic_urls = pic_urls;
		}

		public List<AnnotationsBean> getAnnotations() {
			return annotations;
		}

		public void setAnnotations(List<AnnotationsBean> annotations) {
			this.annotations = annotations;
		}

		public List<?> getDarwin_tags() {
			return darwin_tags;
		}

		public void setDarwin_tags(List<?> darwin_tags) {
			this.darwin_tags = darwin_tags;
		}

		public List<?> getHot_weibo_tags() {
			return hot_weibo_tags;
		}

		public void setHot_weibo_tags(List<?> hot_weibo_tags) {
			this.hot_weibo_tags = hot_weibo_tags;
		}

		public List<?> getText_tag_tips() {
			return text_tag_tips;
		}

		public void setText_tag_tips(List<?> text_tag_tips) {
			this.text_tag_tips = text_tag_tips;
		}

		public static class UserBean implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = 13L;
			private long id;
			private String idstr;
			private String screen_name;
			private String name;
			private String province;
			private String city;
			private String location;
			private String description;
			private String url;
			private String profile_image_url;
			private String cover_image_phone;
			private String profile_url;
			private String domain;
			private String weihao;
			private String gender;
			private int followers_count;
			private int friends_count;
			private int pagefriends_count;
			private int statuses_count;
			private int favourites_count;
			private String created_at;
			private boolean following;
			private boolean allow_all_act_msg;
			private boolean geo_enabled;
			private boolean verified;
			private int verified_type;
			private String remark;
			private int ptype;
			private boolean allow_all_comment;
			private String avatar_large;
			private String avatar_hd;
			private String verified_reason;
			private String verified_trade;
			private String verified_reason_url;
			private String verified_source;
			private String verified_source_url;
			private int verified_state;
			private int verified_level;
			private int verified_type_ext;
			private String verified_reason_modified;
			private String verified_contact_name;
			private String verified_contact_email;
			private String verified_contact_mobile;
			private boolean follow_me;
			private int online_status;
			private int bi_followers_count;
			private String lang;
			private int star;
			private int mbtype;
			private int mbrank;
			private int block_word;
			private int block_app;
			private int credit_score;
			private int user_ability;
			private String cardid;
			private String avatargj_id;
			private int urank;

			public long getId() {
				return id;
			}

			public void setId(long id) {
				this.id = id;
			}

			public String getIdstr() {
				return idstr;
			}

			public void setIdstr(String idstr) {
				this.idstr = idstr;
			}

			public String getScreen_name() {
				return screen_name;
			}

			public void setScreen_name(String screen_name) {
				this.screen_name = screen_name;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getProvince() {
				return province;
			}

			public void setProvince(String province) {
				this.province = province;
			}

			public String getCity() {
				return city;
			}

			public void setCity(String city) {
				this.city = city;
			}

			public String getLocation() {
				return location;
			}

			public void setLocation(String location) {
				this.location = location;
			}

			public String getDescription() {
				return description;
			}

			public void setDescription(String description) {
				this.description = description;
			}

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getProfile_image_url() {
				return profile_image_url;
			}

			public void setProfile_image_url(String profile_image_url) {
				this.profile_image_url = profile_image_url;
			}

			public String getCover_image_phone() {
				return cover_image_phone;
			}

			public void setCover_image_phone(String cover_image_phone) {
				this.cover_image_phone = cover_image_phone;
			}

			public String getProfile_url() {
				return profile_url;
			}

			public void setProfile_url(String profile_url) {
				this.profile_url = profile_url;
			}

			public String getDomain() {
				return domain;
			}

			public void setDomain(String domain) {
				this.domain = domain;
			}

			public String getWeihao() {
				return weihao;
			}

			public void setWeihao(String weihao) {
				this.weihao = weihao;
			}

			public String getGender() {
				return gender;
			}

			public void setGender(String gender) {
				this.gender = gender;
			}

			public int getFollowers_count() {
				return followers_count;
			}

			public void setFollowers_count(int followers_count) {
				this.followers_count = followers_count;
			}

			public int getFriends_count() {
				return friends_count;
			}

			public void setFriends_count(int friends_count) {
				this.friends_count = friends_count;
			}

			public int getPagefriends_count() {
				return pagefriends_count;
			}

			public void setPagefriends_count(int pagefriends_count) {
				this.pagefriends_count = pagefriends_count;
			}

			public int getStatuses_count() {
				return statuses_count;
			}

			public void setStatuses_count(int statuses_count) {
				this.statuses_count = statuses_count;
			}

			public int getFavourites_count() {
				return favourites_count;
			}

			public void setFavourites_count(int favourites_count) {
				this.favourites_count = favourites_count;
			}

			public String getCreated_at() {
				return created_at;
			}

			public void setCreated_at(String created_at) {
				this.created_at = created_at;
			}

			public boolean isFollowing() {
				return following;
			}

			public void setFollowing(boolean following) {
				this.following = following;
			}

			public boolean isAllow_all_act_msg() {
				return allow_all_act_msg;
			}

			public void setAllow_all_act_msg(boolean allow_all_act_msg) {
				this.allow_all_act_msg = allow_all_act_msg;
			}

			public boolean isGeo_enabled() {
				return geo_enabled;
			}

			public void setGeo_enabled(boolean geo_enabled) {
				this.geo_enabled = geo_enabled;
			}

			public boolean isVerified() {
				return verified;
			}

			public void setVerified(boolean verified) {
				this.verified = verified;
			}

			public int getVerified_type() {
				return verified_type;
			}

			public void setVerified_type(int verified_type) {
				this.verified_type = verified_type;
			}

			public String getRemark() {
				return remark;
			}

			public void setRemark(String remark) {
				this.remark = remark;
			}

			public int getPtype() {
				return ptype;
			}

			public void setPtype(int ptype) {
				this.ptype = ptype;
			}

			public boolean isAllow_all_comment() {
				return allow_all_comment;
			}

			public void setAllow_all_comment(boolean allow_all_comment) {
				this.allow_all_comment = allow_all_comment;
			}

			public String getAvatar_large() {
				return avatar_large;
			}

			public void setAvatar_large(String avatar_large) {
				this.avatar_large = avatar_large;
			}

			public String getAvatar_hd() {
				return avatar_hd;
			}

			public void setAvatar_hd(String avatar_hd) {
				this.avatar_hd = avatar_hd;
			}

			public String getVerified_reason() {
				return verified_reason;
			}

			public void setVerified_reason(String verified_reason) {
				this.verified_reason = verified_reason;
			}

			public String getVerified_trade() {
				return verified_trade;
			}

			public void setVerified_trade(String verified_trade) {
				this.verified_trade = verified_trade;
			}

			public String getVerified_reason_url() {
				return verified_reason_url;
			}

			public void setVerified_reason_url(String verified_reason_url) {
				this.verified_reason_url = verified_reason_url;
			}

			public String getVerified_source() {
				return verified_source;
			}

			public void setVerified_source(String verified_source) {
				this.verified_source = verified_source;
			}

			public String getVerified_source_url() {
				return verified_source_url;
			}

			public void setVerified_source_url(String verified_source_url) {
				this.verified_source_url = verified_source_url;
			}

			public int getVerified_state() {
				return verified_state;
			}

			public void setVerified_state(int verified_state) {
				this.verified_state = verified_state;
			}

			public int getVerified_level() {
				return verified_level;
			}

			public void setVerified_level(int verified_level) {
				this.verified_level = verified_level;
			}

			public int getVerified_type_ext() {
				return verified_type_ext;
			}

			public void setVerified_type_ext(int verified_type_ext) {
				this.verified_type_ext = verified_type_ext;
			}

			public String getVerified_reason_modified() {
				return verified_reason_modified;
			}

			public void setVerified_reason_modified(
					String verified_reason_modified) {
				this.verified_reason_modified = verified_reason_modified;
			}

			public String getVerified_contact_name() {
				return verified_contact_name;
			}

			public void setVerified_contact_name(String verified_contact_name) {
				this.verified_contact_name = verified_contact_name;
			}

			public String getVerified_contact_email() {
				return verified_contact_email;
			}

			public void setVerified_contact_email(String verified_contact_email) {
				this.verified_contact_email = verified_contact_email;
			}

			public String getVerified_contact_mobile() {
				return verified_contact_mobile;
			}

			public void setVerified_contact_mobile(
					String verified_contact_mobile) {
				this.verified_contact_mobile = verified_contact_mobile;
			}

			public boolean isFollow_me() {
				return follow_me;
			}

			public void setFollow_me(boolean follow_me) {
				this.follow_me = follow_me;
			}

			public int getOnline_status() {
				return online_status;
			}

			public void setOnline_status(int online_status) {
				this.online_status = online_status;
			}

			public int getBi_followers_count() {
				return bi_followers_count;
			}

			public void setBi_followers_count(int bi_followers_count) {
				this.bi_followers_count = bi_followers_count;
			}

			public String getLang() {
				return lang;
			}

			public void setLang(String lang) {
				this.lang = lang;
			}

			public int getStar() {
				return star;
			}

			public void setStar(int star) {
				this.star = star;
			}

			public int getMbtype() {
				return mbtype;
			}

			public void setMbtype(int mbtype) {
				this.mbtype = mbtype;
			}

			public int getMbrank() {
				return mbrank;
			}

			public void setMbrank(int mbrank) {
				this.mbrank = mbrank;
			}

			public int getBlock_word() {
				return block_word;
			}

			public void setBlock_word(int block_word) {
				this.block_word = block_word;
			}

			public int getBlock_app() {
				return block_app;
			}

			public void setBlock_app(int block_app) {
				this.block_app = block_app;
			}

			public int getCredit_score() {
				return credit_score;
			}

			public void setCredit_score(int credit_score) {
				this.credit_score = credit_score;
			}

			public int getUser_ability() {
				return user_ability;
			}

			public void setUser_ability(int user_ability) {
				this.user_ability = user_ability;
			}

			public String getCardid() {
				return cardid;
			}

			public void setCardid(String cardid) {
				this.cardid = cardid;
			}

			public String getAvatargj_id() {
				return avatargj_id;
			}

			public void setAvatargj_id(String avatargj_id) {
				this.avatargj_id = avatargj_id;
			}

			public int getUrank() {
				return urank;
			}

			public void setUrank(int urank) {
				this.urank = urank;
			}
		}

		public static class VisibleBean implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = 14L;
			private int type;
			private int list_id;

			public int getType() {
				return type;
			}

			public void setType(int type) {
				this.type = type;
			}

			public int getList_id() {
				return list_id;
			}

			public void setList_id(int list_id) {
				this.list_id = list_id;
			}
		}

		public static class PicUrlsBean implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = 15L;
			private String thumbnail_pic;

			public String getThumbnail_pic() {
				return thumbnail_pic;
			}

			public void setThumbnail_pic(String thumbnail_pic) {
				this.thumbnail_pic = thumbnail_pic;
			}
		}

		public static class AnnotationsBean implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = 16L;
			private String client_mblogid;

			public String getClient_mblogid() {
				return client_mblogid;
			}

			public void setClient_mblogid(String client_mblogid) {
				this.client_mblogid = client_mblogid;
			}
		}
	}
}
