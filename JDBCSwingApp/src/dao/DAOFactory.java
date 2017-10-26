package dao;


import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

/**
 * Classe qui permet de se connecter &agrave; l abase de donn&eacute;es selon un fichier de properties où sont renseign&eacute;es les informations de connexion.
 * @author R&eacute;mi OGNARD
 * @version 1.0
 */
public class DAOFactory {

	/**
	 * Emplacement du fichier de configuration.
	 */
	private static final String FICHIER_PROPERTIES = "dao/dao.properties";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
	private static final String PROPERTY_MOT_DE_PASSE = "motdepasse";
	
	private String url;
	private String username;
	private String password;

	private Connection connexion;
	
	public DAOFactory() {
		
		Properties properties = new Properties();
		String driver;
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );

		if ( fichierProperties == null ) {
			throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
		}
		try {
			properties.load( fichierProperties );
			this.url = properties.getProperty( PROPERTY_URL );
			driver = properties.getProperty( PROPERTY_DRIVER );
			this.username = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
			this.password = properties.getProperty( PROPERTY_MOT_DE_PASSE );
		} catch ( IOException e ) {
			throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
		}

		try {
			Class.forName( driver );
		} catch ( ClassNotFoundException e ) {
			throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
		}
		
		try {
			connexion = this.getConnection();
			System.out.println("Connexion à la base de données.");
		} catch ( SQLException e ) {
			throw new DAOConfigurationException( "Erreur SQL;", e );
		}
	}
	
	public DAOFactory( String url, String username, String password ) {
		this.url = url;
		this.username = username;
		this.password = password;
		
		if ( url == null ) {
			throw new DAOConfigurationException( "L'url n'est pas renseignée." );
		}
		
		Properties properties = new Properties();
		String driver;
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );

		if ( fichierProperties == null ) {
			throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
		}
		
		try {
			properties.load( fichierProperties );
			driver = properties.getProperty( PROPERTY_DRIVER );
		} catch ( IOException e ) {
			throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
		}

		try {
			Class.forName( driver );
		} catch ( ClassNotFoundException e ) {
			throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
		}
		
		try {
			connexion = this.getConnection();
			System.out.println("Connexion à la base de données.");
		} catch ( SQLException e ) {
			e.printStackTrace();
			
			throw new DAOConfigurationException( "Erreur SQL;", e );
			
		}
	}	

//	/**
//	 * M&eacute;thode charg&eacute;e de r&eacute;cup&eacute;rer les informations de connexion &agrave; la base de
//	 * donn&eacute;es, charger le driver JDBC et retourner une instance de la Factory
//	 * @throws DAOConfigurationException
//	 */
//	public static DaoFactory getInstance() throws DAOConfigurationException {
//
//		Properties properties = new Properties();
//		String url;
//		String driver;
//		String nomUtilisateur;
//		String motDePasse;
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//		InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );
//
//		if ( fichierProperties == null ) {
//			throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
//		}
//		try {
//			properties.load( fichierProperties );
//			url = properties.getProperty( PROPERTY_URL );
//			driver = properties.getProperty( PROPERTY_DRIVER );
//			nomUtilisateur = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
//			motDePasse = properties.getProperty( PROPERTY_MOT_DE_PASSE );
//		} catch ( IOException e ) {
//			throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
//		}
//
//		try {
//			Class.forName( driver );
//		} catch ( ClassNotFoundException e ) {
//			throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
//		}
//
//		DaoFactory instance = new DaoFactory( url, nomUtilisateur, motDePasse );
//		return instance;
//	}

	/** M&eacute;thode charg&eacute;e de fournir une connexion &agrave; la base de donn&eacute;es */
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection( url, username, password );
	}

//	/** M&eacute;thode charg&eacute;e de fournir une connexion &agrave; la base de donn&eacute;es */
//	private Connection getConnection(String url, String username, String password ) throws SQLException {
//		return DriverManager.getConnection( url, username, password );
//	}
	
	public Connection getConnexion() {
	
		return connexion;
	}
	
}
