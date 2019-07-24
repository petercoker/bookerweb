package com.booker.entities;

import com.booker.constants.KidFriendlyStatusEnum;

public abstract class Bookmark {
	private long id;
	private String title;
	private String profileUrl;
	private KidFriendlyStatusEnum kidFrienldyStatus = KidFriendlyStatusEnum.UNKNOWN; // this will tell whether a bookmark is kid friendly
																	// or not
	private User kidFriendlyMarkedBy; // create a record of users who performed this action
	private User shareBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public abstract boolean isKidFriendlyEligible();

	public KidFriendlyStatusEnum getKidFrienldyStatus() {
		return kidFrienldyStatus;
	}

	public void setKidFrienldyStatus(KidFriendlyStatusEnum kidFrienldyStatus) {
		this.kidFrienldyStatus = kidFrienldyStatus;
	}

	public User getKidFriendlyMarkedBy() {
		return kidFriendlyMarkedBy;
	}

	public void setKidFriendlyMarkedBy(User kidFriendlyMarkedBy) {
		this.kidFriendlyMarkedBy = kidFriendlyMarkedBy;
	}

	public User getShareBy() {
		return shareBy;
	}

	public void setShareBy(User shareBy) {
		this.shareBy = shareBy;
	}
}
