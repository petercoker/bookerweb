package com.booker;

import java.util.List;

import com.booker.constants.KidFriendlyStatusEnum;
import com.booker.constants.UserTypeEnum;
import com.booker.controller.BookmarkController;
import com.booker.entities.Bookmark;
import com.booker.entities.User;
import com.booker.partner.IShareable;

public class View {

	// This how the user a bookmark
	// When the user login and is browsing and their presented with all the bookmark
	// looking at each bookmark and when the users is looking at the bookmark,
	// The user may performane three actions 1, bookmark a particular item the are
	// looking at
	// 2, or in case if it is a editor or chief editor user then the user can make
	// the bookmark as kidfriendly
	// 3, share the bookmark with a particular site 4, or the user might not even do
	// anything (These are the action
	// of a user.
	public static void browse(User user, List<List<Bookmark>> bookmarks) {
		// User will browse through all the bookmarks
		// and for each bookmark, we would use a randomization
		// to decide whether the user is going to bookmakr
		// StringBuilder sb = new StringBuilder();
		// sb.append("\n")
		System.out.println("\n" + user.getEmail() + " is browsing items..."); // actually user browsing the item

		int bookmarkCount = 0;

		// Nested for loop because we have a two dimensional array for the bookmark
		for (List<Bookmark> bookmarkList : bookmarks) {
			for (Bookmark bookmark : bookmarkList) {
				// User make the decision whether to Bookmark or not 
				//if (bookmarkCount < DataStore.USER_BOOKMARK_LIMIT) {
					boolean isBookmarked = getBookMarkDecision(bookmark);
					if (isBookmarked) {
						bookmarkCount++; // The user is bookmarking a new item

						BookmarkController.getInstance().saveUserBookMark(user, bookmark); //back-end job of passing user infor and bookmark infor
						System.out.println("New item bookmarked -- " + bookmark);

					}
				//}

				// If user is an Editor or Cheif Editor
				// Mark as kid-friendly (which can only be done by "Editor" or "Chief Editor")
				// Decision is perfumed at each bookmark item level
				if (user.getUserType().equals(UserTypeEnum.EDITOR)
						|| user.getUserType().equals(UserTypeEnum.CHIEF_EDITOR)) {

					// See if it can be Mark as kid-friendly (which can only be done by "Editor" or "Chief Editor")
					if (bookmark.isKidFriendlyEligible()
							&& bookmark.getKidFrienldyStatus().equals(KidFriendlyStatusEnum.UNKNOWN)) {
						
						//if it is in an unknown set, then get the decision
						KidFriendlyStatusEnum kidFriendlyStatus = getKidFriendlyStatusDecision(bookmark);
						
						//if kidfriendly status is NOT unknown, Only then we update the kidfriendly status
						if (!kidFriendlyStatus.equals(KidFriendlyStatusEnum.UNKNOWN)) {
							BookmarkController.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);
							// pass infor of user who marked,
						}
					}

					// Sharing method
					// bookmark has to be either a book or weblink (only book and weblink implement shareable interface
					// If sharing of kid friendly bookmark is approved
					if (bookmark.getKidFrienldyStatus().equals(KidFriendlyStatusEnum.APPROVED)
							&& bookmark instanceof IShareable) {
						boolean isBookmarkShared = getSharedDecision();

						if (isBookmarkShared) {
							//pass this through the back-end
							BookmarkController.getInstance().share(user, bookmark);
						}

					}
				}

			}
		}

	}

	// Task: simulate user input
	private static boolean getSharedDecision() {
		return Math.random() < 0.5 ? true : false;

	}

	// random kidfriendly decision
	private static KidFriendlyStatusEnum getKidFriendlyStatusDecision(Bookmark bookmark) {
		double decision = Math.random();
		
//		return Math.random() < 0.4 ? KidFriendlyStatusEnum.APPROVED
//				: (Math.random() >= 0.4 && Math.random() < 0.8) ? KidFriendlyStatusEnum.REJECTED
//						: KidFriendlyStatusEnum.UNKNOWN;
		
		return decision < 0.4 ? KidFriendlyStatusEnum.APPROVED
				: (decision >= 0.4 && decision < 0.8) ? KidFriendlyStatusEnum.REJECTED
						: KidFriendlyStatusEnum.UNKNOWN;

	}

	private static boolean getBookMarkDecision(Bookmark bookmark) {
		return Math.random() < 0.5 ? true : false;
		// if value is less than "true" user wants to bookmark
		// "false" user does not want to bookmark

	}

	/*
	 * //This how the user a bookmark public static void bookmark(User user,
	 * Bookmark[][] bookmarks) { //StringBuilder sb = new StringBuilder();
	 * //sb.append("\n") System.out.println("\n" + user.getEmail() +
	 * "is bookmarking");
	 * 
	 * //random selection process for (int i = 0; i < DataStore.USER_BOOKMARK_LIMIT;
	 * i++) {
	 * 
	 * //randomly select one the bookmarks from Bookmark[][] int typeOffset =
	 * (int)(Math.random() * DataStore.BOOKMARK_TYPES_COUNT);//select type of
	 * bookmark int bookmarkOffset = (int)(Math.random() *
	 * DataStore.BOOKMARK_COUNT_PER_TYPE); //select bookmark within the type of
	 * bookmark, which has a limit
	 * 
	 * //Actual selection of the bookmark, which includes type of bookmark and
	 * bookmark item Bookmark bookmark = bookmarks[typeOffset][bookmarkOffset];
	 * 
	 * 
	 * //after the user has randomly selected a bookmark, //they will save it. The
	 * information will be passed to the //backend server (database) and it will
	 * reach the controller //then it will be passed to the server layer. which will
	 * be sent //to the controller
	 * 
	 * 
	 * BookmarkController.getInstance().saveUserBookMark(user, bookmark);
	 * System.out.println(bookmark);
	 * 
	 * } }
	 * 
	 */
}
