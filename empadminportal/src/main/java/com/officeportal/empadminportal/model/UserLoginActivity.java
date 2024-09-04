package com.officeportal.empadminportal.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "userLogin_activity")
public class UserLoginActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ActivityId;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	private LocalDateTime loginTime;

	public long getActivityId() {
		return ActivityId;
	}

	public void setActivityId(long activityId) {
		ActivityId = activityId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}

}
