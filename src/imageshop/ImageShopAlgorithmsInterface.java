package imageshop;

public interface ImageShopAlgorithmsInterface {
	// The methods that must be implemented in ImageShopAlgorithms.java
	public GImage flipHorizontal(GImage source);
	public GImage rotateLeft(GImage source);
	public GImage rotateRight(GImage source);
	public GImage greenScreen(GImage source);
	public GImage equalize(GImage source);
	public GImage negative(GImage source);
	public GImage translate(GImage source, int dx, int dy);
	public GImage blur(GImage source);
	
	/* Helper method to compute the luminosity of an RGB pixel (from 11.7 in the book) */
	public default int computeLuminosity(int r, int g, int b) {
		return (int)Math.round(0.299 * r + 0.587 * g + 0.114 * b); 
	}
}
