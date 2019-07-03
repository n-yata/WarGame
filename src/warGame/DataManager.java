package warGame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataManager {
	private static String desktopDir = System.getProperty("user.home") + "/Desktop";
	//中断データ
	private static final String INTERRUPT_FILE = desktopDir + "/game_interrupt.dat";
	//結果データ
	private static final String RESULT_FILE = desktopDir + "/game_result.csv";

	public GameMaster load() {
		GameMaster gameMaster = new GameMaster();
		Path filePath = Paths.get(INTERRUPT_FILE);

		if (Files.exists(filePath)) {
			System.out.println("セーブデータを読み込みますか？ [y: 読み込み n:新規でスタート]");

			LOOP: while (true) {
				String choise = new java.util.Scanner(System.in).nextLine();

				switch (choise) {
				case "y":
					System.out.println("セーブデータを読み込みます。");
					try (ObjectInputStream is = new ObjectInputStream(
							Files.newInputStream(Paths.get(INTERRUPT_FILE)));) {
						gameMaster = (GameMaster) is.readObject();
					} catch (IOException | ReflectiveOperationException e) {
						e.printStackTrace();
					}
					break LOOP;
				case "n":
					System.out.println("新規でゲームを開始します。");
					break LOOP;
				default:
					System.out.println("yかnを入力してください。");
				}
			}
		} else {
			System.out.println("新規でゲームを開始します。");
		}
		return gameMaster;
	}

	public boolean save(GameMaster gameMaster) {
		Path filePath = Paths.get(INTERRUPT_FILE);
		try (
				ObjectOutputStream os = new ObjectOutputStream(Files.newOutputStream(filePath));) {
			os.writeObject(gameMaster);
			os.flush();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public boolean deleteInterruptFile() {
		Path filePath = Paths.get(INTERRUPT_FILE);

		if (Files.exists(filePath)) {
			try {
				Files.delete(Paths.get(INTERRUPT_FILE));
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		return false;
	}

	public boolean resultToCsv(GameResult result, int acquis) {
		String comment1 = "ゲーム回数";
		String comment2 = "勝利回数";
		String comment3 = "最大カード枚数";
		String[] values = { "0", "0", "0" };
		Path filePath = Paths.get(RESULT_FILE);

		if (Files.exists(filePath)) {
			try (
					BufferedReader in = Files.newBufferedReader(filePath);) {
				while (true) {
					String line = in.readLine();
					if (line == null) {
						break;
					}
					values = line.split(",");
				}
				Files.delete(filePath);
			} catch (IOException e) {
				return false;
			}

		}

		//ゲーム回数を1増やす
		values[0] = String.valueOf(Integer.parseInt(values[0]) + 1);

		//ゲームに勝ったら勝利回数を１増やす
		if (result.equals(GameResult.WIN)) {
			values[1] = String.valueOf(Integer.parseInt(values[1]) + 1);

			//勝利時、カード獲得枚数が最大であれば更新
			if (acquis > Integer.parseInt(values[2])) {
				values[2] = String.valueOf(acquis);
			}

		}

		try (
				BufferedWriter out = Files.newBufferedWriter(filePath);) {
			out.write(
					String.format("%s,%s,%s%n%s,%s,%s",
							comment1, comment2, comment3, values[0], values[1], values[2]));
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
