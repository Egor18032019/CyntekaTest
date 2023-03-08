import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static BufferedReader reader = null;
    public static int M; //количество строк первого множества
    public static int N; // количество строк второго множества

    // TODO чтение и запись в файл сделать
    public static void main(String[] args) throws Exception {
        init();
        run();
    }

    private static void init() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /*
    в ТЗ нечетко была дано определение формулы похожести
    поэтому я в этом решение определил похожесть как соотношение одинаковых символов к длине.
     */
    private static void run() throws IOException {
        N = Integer.parseInt(reader.readLine());
        Map<Integer, String> strorageFirst = new HashMap<>();
        Map<Integer, String> strorageSecondFixed = new HashMap<>();
        for (int i = 1; i <= N; i++) {
            String line = reader.readLine().toLowerCase();
            strorageFirst.put(i, line);
        }
        M = Integer.parseInt(reader.readLine());
        Map<Integer, String> strorageSecond = new HashMap<>();
        for (int i = 1; i <= M; i++) {
            String line = reader.readLine().toLowerCase();
            strorageSecond.put(i, line);
            strorageSecondFixed.put(i, line);
        }
        // сложили всё в хранилище и далее считаем
        // порядковый номер строки из первого множество и [порядковый номер строки из второе множество и похожесть]
        Map<Integer, int[]> strorage = new HashMap<>();
        for (int i = 1; i <= N; i++) {
            String line = strorageFirst.get(i);
            String[] firstLineArr = line.split(" ");
            int count = 0;
            for (int q = 1; q <= M; q++) {
                String lineSecond = strorageSecond.get(q);
                String[] secondLineArr = lineSecond.split(" ");
                for (int a = 0; a < firstLineArr.length; a++) {
                    char[] charArrFist = firstLineArr[a].toCharArray();
                    for (int z = 0; z < secondLineArr.length; z++) {
                        char[] charArrSecond = secondLineArr[z].toCharArray();
//                из первого множества со строками из второго множества (одна к одной)
                        int countSimilar = 0;
                        for (int w = 0; w < charArrFist.length; w++) {
                            if (w < charArrSecond.length) {
                                if (charArrFist[w] == charArrSecond[w]) countSimilar++;
                            }
                        }
                        int percent = countSimilar * 100 / charArrFist.length; // 800/9=88
                        // считаем процент похожести и если он больше 50 то увеличиваем счетчик похожести.
                        if (percent > 50) {
                            count++;
                        }
                    }
                }
                int last = strorage.getOrDefault(i, new int[2])[1];
                if (count > last) {
                    int[] value = new int[2];
                    value[0] = q;
                    value[1] = count;
                    strorage.put(i, value);
                }
            }
        }


        for (Map.Entry<Integer, int[]> entry : strorage.entrySet()) {
            int key = entry.getValue()[0];
            strorageSecondFixed.remove(key);
        }


        for (int i = 1; i <= N; i++) {
            StringBuilder str = new StringBuilder();
            if (strorage.containsKey(i)) {
                int key = strorage.get(i)[0];
                str.append(strorageFirst.get(i))
                        .append(" : ")
                        .append(strorageSecond.get(key));
                System.out.println(str);
                continue;
            }
            str.append(strorageFirst.get(i))
                    .append(" : ");
            boolean flag = false;
            for (Map.Entry<Integer, String> entry : strorageSecondFixed.entrySet()) {
                flag = true;
                str.append(entry.getValue());
                strorageSecondFixed.remove(entry.getKey());
                break;
            }
            if (flag) {
                System.out.println(str);
            } else {
                str.append("?");
                System.out.println(str);
            }
        }
        for (Map.Entry<Integer, String> entry : strorageSecondFixed.entrySet()) {
            StringBuilder str = new StringBuilder();
            str.append(entry.getValue())
                    .append(" : ")
                    .append("?");
            System.out.println(str);
        }

    }
}
