package model.callGraph;

/**
 * Holds one call from a call graph.<br>
 * Class A calls class B if there exists a call between a method of class A and
 * a method of class B.
 * 
 * @author Alex Mikulinski
 * @since Jun 28, 2018
 */
public class Call {
	/** Call between (C)lasses or (M)ethods. */
	private String				call_type;
	/** The <b>F</b>ully <b>Q</b>ualified <b>N</b>ame (FQN) of the caller. */
	private String				caller;
	private MethodCallType	method_call_type;
	/** The <b>F</b>ully <b>Q</b>ualified <b>N</b>ame (FQN) of the callee. */
	private String				callee;

	/**
	 * Returns the {@link #call_type} of current {@link Call}.
	 *
	 * @return The call_type as {@link String}.
	 */
	public String getCall_type() {
		return this.call_type;
	}

	/**
	 * @param call_type
	 *           The call_type to set.
	 */
	public void setCall_type(String call_type) {
		this.call_type = call_type;
	}

	/**
	 * Returns the {@link #caller} of current {@link Call}.
	 *
	 * @return The caller as {@link String}.
	 */
	public String getCaller() {
		return this.caller;
	}

	/**
	 * @param caller
	 *           The caller to set.
	 */
	public void setCaller(String caller) {
		this.caller = caller;
	}

	/**
	 * Returns the {@link #callee} of current {@link Call}.
	 *
	 * @return The callee as {@link String}.
	 */
	public String getCallee() {
		return this.callee;
	}

	/**
	 * @param callee
	 *           The callee to set.
	 */
	public void setCallee(String callee) {
		this.callee = callee;
	}

	/**
	 * Returns the {@link #method_call_type} of current {@link Call}.
	 *
	 * @return The method_call_type as {@link MethodCallType}.
	 */
	public MethodCallType getMethod_call_type() {
		return this.method_call_type;
	}

	/**
	 * @param method_call_type
	 *           The method_call_type to set.
	 */
	public void setMethod_call_type(MethodCallType method_call_type) {
		this.method_call_type = method_call_type;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Call [");
		if (this.call_type != null) {
			builder.append("call_type=");
			builder.append(this.call_type);
			builder.append(", ");
		}
		if (this.caller != null) {
			builder.append("caller=");
			builder.append(this.caller);
			builder.append(", ");
		}
		if (this.method_call_type != null) {
			builder.append("method_call_type=");
			builder.append(this.method_call_type);
			builder.append(", ");
		}
		if (this.callee != null) {
			builder.append("callee=");
			builder.append(this.callee);
		}
		builder.append("]\n");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int	prime		= 31;
		int			result	= 1;
		result	= prime * result + ((this.call_type == null) ? 0 : this.call_type.hashCode());
		result	= prime * result + ((this.callee == null) ? 0 : this.callee.hashCode());
		result	= prime * result + ((this.caller == null) ? 0 : this.caller.hashCode());
		result	= prime * result + ((this.method_call_type == null) ? 0 : this.method_call_type.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Call)) {
			return false;
		}
		Call other = (Call) obj;
		if (this.call_type == null) {
			if (other.call_type != null) {
				return false;
			}
		} else if (!this.call_type.equals(other.call_type)) {
			return false;
		}
		if (this.callee == null) {
			if (other.callee != null) {
				return false;
			}
		} else if (!this.callee.equals(other.callee)) {
			return false;
		}
		if (this.caller == null) {
			if (other.caller != null) {
				return false;
			}
		} else if (!this.caller.equals(other.caller)) {
			return false;
		}
		if (this.method_call_type != other.method_call_type) {
			return false;
		}
		return true;
	}
}
