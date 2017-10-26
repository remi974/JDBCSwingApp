package dao;

/**
 * Classe qui permet la gestion de g&eacute;n&eacute;rer des erreurs selon la DAO en place.
 * @author R&eacute;mi OGNARD
 * @version 1.0
 */
public class DAOException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Constructeurs
	 */
	public DAOException( String message ) {

		super( message );
	}

	public DAOException( String message, Throwable cause ) {

		super( message, cause );
	}

	public DAOException( Throwable cause ) {

		super( cause );
	}
}
