package warGame;

public class WarGame {
	public static void main(String[] args) {
		DataManager dataManager = new DataManager();
		GameMaster gameMaster = dataManager.load();
		gameMaster.start();
	}
}
