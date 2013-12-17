package edu.uwm.cs361.fantastic_five.training_tracker.app.services;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class PersistenceService {
	private static String propsResource = "transactions-optional";

	public static PersistenceManager getPersistenceManager()
	{
		return getPersistenceManagerFactory().getPersistenceManager();
	}

	public static PersistenceManagerFactory getPersistenceManagerFactory() {
		return JDOHelper.getPersistenceManagerFactory(PersistenceService.propsResource);
	}
}
