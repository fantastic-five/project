package edu.uwm.cs361.fantastic_five.training_tracker.app.entities;

import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

import edu.uwm.cs361.fantastic_five.training_tracker.app.services.PasswordService;

@PersistenceCapable
public class User {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String username;

	@Persistent
	private String passwordHash;

	@Persistent
	private String passwordSalt;

	@Persistent
	private Set<Key> accesses;

	public User(String username, String password) {
		this.username = username;
		this.passwordSalt = generateSalt();
		this.passwordHash = PasswordService.hash(password, this.passwordSalt);
	}

	public void setPassword(String password) {
		this.passwordSalt = PasswordService.generateSalt(32); // Perfect opportunity to reset the salt
		this.passwordHash = PasswordService.hash(password, this.passwordSalt);
	}
	public boolean passwordMatches(String password) {
		return PasswordService.check(password, passwordSalt, passwordHash);
	}

	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public Key getKey() {
		return this.key;
	}

	private String generateSalt() {
		return PasswordService.generateSalt(32);
	}
}
