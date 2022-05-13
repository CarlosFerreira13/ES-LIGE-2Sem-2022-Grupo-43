package org.apache.ibatis.datasource.pooled;


import java.sql.SQLException;

public class PooledConnectionProduct {
	private boolean valid;

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	* Invalidates the connection.
	*/
	public void invalidate() {
		valid = false;
	}

	public void checkConnection() throws SQLException {
		if (!valid) {
			throw new SQLException("Error accessing PooledConnection. Connection is invalid.");
		}
	}
}