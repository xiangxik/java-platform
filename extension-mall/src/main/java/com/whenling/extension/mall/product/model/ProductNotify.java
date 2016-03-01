package com.whenling.extension.mall.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.model.BaseEntity;

@Entity
@Table(name = "mall_product_notify")
public class ProductNotify extends BaseEntity<Long> {

	private static final long serialVersionUID = 5845600177200663470L;

	/** E-mail */
	@NotEmpty
	@Email
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	private String email;

	/** 是否已发送 */
	@Column(nullable = false)
	private Boolean hasSent;

	/** 会员 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	private User user;

	/** 商品 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Product product;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getHasSent() {
		return hasSent;
	}

	public void setHasSent(Boolean hasSent) {
		this.hasSent = hasSent;
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

}
