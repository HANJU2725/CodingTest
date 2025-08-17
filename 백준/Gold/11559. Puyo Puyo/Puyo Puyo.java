import java.awt.*;
import java.io.*;
import java.util.*;

public class Main {
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    static boolean inRange(int x, int y) {
        return 0 <= x && x < 12 && 0 <= y && y < 6;
    }
    static char[][] map;
    static Queue<Point> q;
    static ArrayList<Point> list;
    static boolean[][] visited;

    static int start() {
        int result = 0;
        while (true) {
            visited = new boolean[12][6];
            int count = 0;
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 6; j++) {
                    if (map[i][j] != '.' && !visited[i][j]) {
                        visited[i][j] = true;
                        count += search(map[i][j], i, j);
                    }
                }
            }
            if (count == 0)
                break;
            result++;
            for (int j = 0; j < 6; j++) {
                int height = 11;
                for (int i = 11; i >= 0; i--) {
                    if (map[i][j] != '.') {
                        if(height != i){
                            map[height][j] = map[i][j];
                            map[i][j] = '.';
                        }
                        height--;
                    }
                }
            }
        }
        return result;
    }

    static int search(char puyo, int x, int y) {
        q = new ArrayDeque<>();
        list = new ArrayList<>();
        q.add(new Point(x, y));
        while (!q.isEmpty()) {
            Point cur = q.poll();
            list.add(cur);
            for (int d = 0; d < 4; d++) {
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];
                if (!inRange(nx, ny) || visited[nx][ny] || map[nx][ny] != puyo) continue;
                q.add(new Point(nx, ny));
                visited[nx][ny] = true;
            }
        }
        if (list.size() >= 4) {
            for (Point cur : list){
                map[cur.x][cur.y] = '.';
            }
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        map = new char[12][6];

        for (int i = 0; i < 12; i++) {
            String str = br.readLine();
            for (int j = 0; j < 6; j++) {
                map[i][j] = str.charAt(j);
            }
        }
        System.out.println(start());
    }
}
