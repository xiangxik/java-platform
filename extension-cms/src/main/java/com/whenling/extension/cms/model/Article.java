package com.whenling.extension.cms.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.BizEntity;

@Entity
@Table(name = "sys_article")
public class Article extends BizEntity<User, Long> {

	private static final long serialVersionUID = 8004745715001630351L;

	/** 点击数缓存名称 */
	public static final String HITS_CACHE_NAME = "articleHits";

	/** 点击数缓存更新间隔时间 */
	public static final int HITS_CACHE_INTERVAL = 600000;

	/** 内容分页长度 */
	private static final int PAGE_CONTENT_LENGTH = 800;

	/** 内容分页符 */
	private static final String PAGE_BREAK_SEPARATOR = "<hr class=\"pageBreak\" />";

	/** 段落分隔符配比 */
	private static final Pattern PARAGRAPH_SEPARATOR_PATTERN = Pattern.compile("[,;\\.!?，；。！？]");

	/** 标题 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String title;

	/** 作者 */
	@Length(max = 200)
	private String author;

	/** 内容 */
	@Lob
	private String content;

	/** 页面标题 */
	@Length(max = 200)
	private String seoTitle;

	/** 页面关键词 */
	@Length(max = 200)
	private String seoKeywords;

	/** 页面描述 */
	@Length(max = 200)
	private String seoDescription;

	/** 是否发布 */
	@NotNull
	@Column(nullable = false)
	private Boolean isPublication;

	/** 是否置顶 */
	@NotNull
	@Column(nullable = false)
	private Boolean isTop;

	/** 点击数 */
	@Column(nullable = false)
	private Long hits;

	/** 页码 */
	@Transient
	private Integer pageNumber;

	/** 文章分类 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private ArticleCategory articleCategory;

	/** 标签 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mall_article_tag")
	@OrderBy("sortNo asc")
	private Set<ArticleTag> tags = new HashSet<ArticleTag>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		if (pageNumber != null) {
			String[] pageContents = getPageContents();
			if (pageNumber < 1) {
				pageNumber = 1;
			}
			if (pageNumber > pageContents.length) {
				pageNumber = pageContents.length;
			}
			return pageContents[pageNumber - 1];
		} else {
			return content;
		}
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public Boolean getIsPublication() {
		return isPublication;
	}

	public void setIsPublication(Boolean isPublication) {
		this.isPublication = isPublication;
	}

	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	public Long getHits() {
		return hits;
	}

	public void setHits(Long hits) {
		this.hits = hits;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	public Set<ArticleTag> getTags() {
		return tags;
	}

	public void setTags(Set<ArticleTag> tags) {
		this.tags = tags;
	}

	/**
	 * 获取文本内容
	 * 
	 * @return
	 */
	@Transient
	public String getText() {
		if (getContent() != null) {
			return Jsoup.parse(getContent()).text();
		}
		return null;
	}

	/**
	 * 获取分页内容
	 * 
	 * @return
	 */
	@Transient
	public String[] getPageContents() {
		if (StringUtils.isEmpty(content)) {
			return new String[] { "" };
		}
		if (content.contains(PAGE_BREAK_SEPARATOR)) {
			return content.split(PAGE_BREAK_SEPARATOR);
		} else {
			List<String> pageContents = new ArrayList<String>();
			Document document = Jsoup.parse(content);
			List<Node> children = document.body().childNodes();
			if (children != null) {
				int textLength = 0;
				StringBuffer html = new StringBuffer();
				for (Node node : children) {
					if (node instanceof Element) {
						Element element = (Element) node;
						html.append(element.outerHtml());
						textLength += element.text().length();
						if (textLength >= PAGE_CONTENT_LENGTH) {
							pageContents.add(html.toString());
							textLength = 0;
							html.setLength(0);
						}
					} else if (node instanceof TextNode) {
						TextNode textNode = (TextNode) node;
						String text = textNode.text();
						String[] contents = PARAGRAPH_SEPARATOR_PATTERN.split(text);
						Matcher matcher = PARAGRAPH_SEPARATOR_PATTERN.matcher(text);
						for (String content : contents) {
							if (matcher.find()) {
								content += matcher.group();
							}
							html.append(content);
							textLength += content.length();
							if (textLength >= PAGE_CONTENT_LENGTH) {
								pageContents.add(html.toString());
								textLength = 0;
								html.setLength(0);
							}
						}
					}
				}
				String pageContent = html.toString();
				if (StringUtils.isNotEmpty(pageContent)) {
					pageContents.add(pageContent);
				}
			}
			return pageContents.toArray(new String[pageContents.size()]);
		}
	}

	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	@Transient
	public int getTotalPages() {
		return getPageContents().length;
	}

}
