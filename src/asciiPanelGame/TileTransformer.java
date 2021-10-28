package asciiPanelGame;

import asciiPanelGame.asciiPanel.AsciiCharacterData;

public interface TileTransformer {
	public void transformTile(int x, int y, AsciiCharacterData data);
}