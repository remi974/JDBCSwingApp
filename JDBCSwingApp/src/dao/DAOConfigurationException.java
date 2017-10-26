package dao;

/**
 * Classe qui permet la gestion de g&eacute;n&eacute;rer des erreurs selon la configuration DAO en place.
 * @author R&eacute;mi OGNARD
 * @version 1.0
 */
public class DAOConfigurationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	* Constructeurs
	*/
	public DAOConfigurationException( String message ) {
	
		super( message );
	}
	
	public DAOConfigurationException( String message, Throwable
	cause ) {
	
		super( message, cause );
	}
	
	public DAOConfigurationException( Throwable cause ) {
	
		super( cause );
	}
}