package warGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class GameMaster implements Serializable{
	private static final long serialVersionUID = 606215554616577266L;

	Player player = new Player();
	Player cpu = new Player();
	//第○回戦目
	int compeNumber = 1;
	//場に積まれたカードの枚数
	ArrayList<Card> storeCard = new ArrayList<>();

	public GameMaster() {
		Trump trump = new Trump();
		trump.createDeck();
		//デッキをシャッフル
		Collections.shuffle(trump.trumpList);
		//1枚ずつデッキから配る
		while (!(trump.trumpList.size() == 0)) {
			if (trump.trumpList.size() % 2 == 0) {
				player.setYourHand(trump.distributeByDeck());
			} else {
				cpu.setYourHand(trump.distributeByDeck());
			}
		}
	}

	public void start() {
		while (!(player.YourHandSize() <= 0 || cpu.YourHandSize() <= 0)) {

			System.out.println("### 第" + compeNumber + "回戦 ###");
			System.out.println("場に積まれた札:" + storeCard.size() + "枚");
			System.out.println("CPUの持ち札:" + cpu.YourHandSize() + "枚,獲得した札:" + cpu.AcuisCardSize() + "枚");
			System.out.println("あなたの持ち札:" + player.YourHandSize() + "枚,獲得した札:" + player.AcuisCardSize() + "枚");
			System.out.println("札を切りますか？（d:札を切る, q:中断）>");
			String choise = new java.util.Scanner(System.in).nextLine();

			LOOP: switch (choise) {
			//札を切る
			case "d":
				Card cpuCard = cpu.drawCard();
				System.out.println("CPUが切った札:" + cpuCard);
				Card playerCard = player.drawCard();
				System.out.println("あなたが切った札:" + playerCard);

				switch (compare(playerCard, cpuCard)) {
				case WIN:
					System.out.println("あなたが獲得しました！");
					player.addAcuisCard(playerCard, cpuCard, storeCard);
					storeCard.clear();
					compeNumber++;
					break LOOP;
				case LOSE:
					System.out.println("CPUが獲得しました！");
					cpu.addAcuisCard(playerCard, cpuCard, storeCard);
					storeCard.clear();
					compeNumber++;
					break LOOP;
				case DRAW:
					System.out.println("引き分けです。");
					storeCard.add(playerCard);
					storeCard.add(cpuCard);
					compeNumber++;
					break LOOP;
				}
			//中断
			case "q":
				System.out.println("ゲームを中断します");
				DataManager dataManager = new DataManager();
				if(dataManager.save(this)) {
					System.out.println("データのセーブが完了しました。");
				}else {
					System.out.println("データのセーブに失敗しました。");
				}
				System.out.println("ゲームを終了します。");
				return;
			default:
				System.out.println("dまたはqの文字を入力してください");
			}
		}

		System.out.println("### 最終結果 ###");
		System.out.println("CPUが獲得した札:" + cpu.AcuisCardSize() + "枚");
		System.out.println("あなたが獲得した札:" + player.AcuisCardSize() + "枚");

		DataManager dataManager = new DataManager();
		GameResult gameResult = GameResult.DRAW;
		switch(result(player.AcuisCardSize(), cpu.AcuisCardSize())) {
		case WIN:
			System.out.println("あなたが勝ちました、おめでとう！");
			gameResult = GameResult.WIN;
			break;
		case LOSE:
			System.out.println("CPUが勝ちました、残念・・・");
			gameResult = GameResult.LOSE;
			break;
		case DRAW:
			System.out.println("引き分けです、惜しい！");
			gameResult = GameResult.DRAW;
			break;
		}
		//場に積まれたカードをクリア
		storeCard.clear();
		if(dataManager.deleteInterruptFile()) {
			System.out.println("中断ファイルを削除しました。");
		}
		if(dataManager.resultToCsv(gameResult, player.AcuisCardSize())) {
			System.out.println("ゲームの結果を記録しました。");
		}else {
			System.out.println("ゲーム結果の記録に失敗しました。");
		}
	}

	//カードの強さを比較
	public GameResult compare(Card player, Card cpu) {
		if (player.getNumber().getStrength() > cpu.getNumber().getStrength()) {
			return GameResult.WIN;
		} else if (player.getNumber().getStrength() < cpu.getNumber().getStrength()) {
			return GameResult.LOSE;
		} else {
			return GameResult.DRAW;
		}
	}

	//最終結果のカード枚数を比較
	public GameResult result(int player, int cpu) {
		if(player > cpu) {
			return GameResult.WIN;
		}else if(player < cpu) {
			return GameResult.LOSE;
		}else {
			return GameResult.DRAW;
		}
	}
}