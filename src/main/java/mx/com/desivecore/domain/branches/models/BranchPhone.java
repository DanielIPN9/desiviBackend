package mx.com.desivecore.domain.branches.models;

public class BranchPhone {

	private String phone;

	private String extension;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return "BranchPhone [phone=" + phone + ", extension=" + extension + "]";
	}

}
