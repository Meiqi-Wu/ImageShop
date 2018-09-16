package imageshop;


public class ImageShopAlgorithms implements ImageShopAlgorithmsInterface{
	public GImage flipHorizontal(GImage source) {
		GImage newImage = new GImage(source);
		newImage.flipHorizontal();
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
		GImage newImage = new GImage(source);
		newImage.greenScreen();
		return newImage;
	}

	public GImage equalize(GImage source) {
		// TODO
		GImage newImage = new GImage(source); 
		newImage.equalize();
		return newImage;
	} 

	public GImage negative(GImage source) {
		GImage newImage = new GImage(source);
		newImage.negative();
		return newImage;
	}

	public GImage translate(GImage source, int dx, int dy) {
		// TODO
		GImage newImage = new GImage(source);
		newImage.translate(dx, dy);
		return newImage;
	}

	public GImage blur(GImage source) {
		// TODO
		GImage newImage = new GImage(source);
		newImage.blur();
		return newImage;
	}

}
