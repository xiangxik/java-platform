package com.whenling.core.support.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
public abstract class BizEntity<U, ID extends Serializable> extends BaseEntity<ID> implements Auditable<U, ID> {

	private static final long serialVersionUID = -2939754862728323127L;

	@ManyToOne(fetch = FetchType.LAZY)
	private U createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	private U lastModifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastModifiedDate;

	@Override
	public U getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(final U createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public DateTime getCreatedDate() {
		return null == createdDate ? null : new DateTime(createdDate);
	}

	@Override
	public void setCreatedDate(final DateTime createdDate) {
		this.createdDate = null == createdDate ? null : createdDate.toDate();
	}

	@Override
	public U getLastModifiedBy() {
		return lastModifiedBy;
	}

	@Override
	public void setLastModifiedBy(final U lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Override
	public DateTime getLastModifiedDate() {
		return null == lastModifiedDate ? null : new DateTime(lastModifiedDate);
	}

	@Override
	public void setLastModifiedDate(DateTime lastModifiedDate) {
		this.lastModifiedDate = null == lastModifiedDate ? null : lastModifiedDate.toDate();
	}
}
