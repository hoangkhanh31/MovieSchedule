package Objects;

import java.io.Serializable;

public class Cineplex implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name, slug, image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
}

