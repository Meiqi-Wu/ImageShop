package imageshop;


public class ImageShopAlgorithms implements ImageShopAlgorithmsInterface{
	public GImage flipHorizontal(GImage source) {
		GImage newImage = new GImage(source);
		newImage.flipHorizontal(true);;
		return newImage;
	}

	public GImage rotateLeft(GImage source) {
		GImage newImage = new GImage(source);
		newImage.rotate(-90);
		return newImage;
	}

	public GImage rotateRight(GImage source) {
		GImage newImage = new GImage(source);
		newImage.rotate(90);
		return newImage;
	}

	public GImage greenScreen(GImage source) {
		// TODO
		return null;
	}

	public GImage equalize(GImage source) {
		// TODO
		return null;
	}

	public GImage negative(GImage source) {
		// TODO
		return null;
	}

	public GImage translate(GImage source, int dx, int dy) {
		// TODO
		return null;
	}

	public GImage blur(GImage source) {
		// TODO
		return null;
	}

}
