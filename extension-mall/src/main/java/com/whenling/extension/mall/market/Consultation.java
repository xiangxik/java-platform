package com.whenling.extension.mall.market;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.extension.mall.product.model.Product;
import com.whenling.module.domain.model.BaseEntity;

@Entity
@Table(name = "mall_consultation")
public class Consultation extends BaseEntity<Long> {

	private static final long serialVersionUID = -7896926422637656370L;

	/** 内容 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	private String content;

	/** 是否显示 */
	@Column(nullable = false)
	private Boolean isShow;

	/** IP */
	@Column(nullable = false, updatable = false)
	private String ip;

	/** 会员 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	private User user;

	/** 商品 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Product product;

	/** 咨询 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	private Consultation forConsultation;

	/** 回复 */
	@OneToMany(mappedBy = "forConsultation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createdDate asc")
	private Set<Consultation> replyConsultations = new HashSet<Consultation>();

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Consultation getForConsultation() {
		return forConsultation;
	}

	public void setForConsultation(Consultation forConsultation) {
		this.forConsultation = forConsultation;
	}

	public Set<Consultation> getReplyConsultations() {
		return replyConsultations;
	}

	public void setReplyConsultations(Set<Consultation> replyConsultations) {
		this.replyConsultations = replyConsultations;
	}

}
