package kmitl.lab03.taweewong58070045.simplemydot.model;

import java.util.ArrayList;
import java.util.List;

public class Dots {
    public interface OnDotsChangedListener {
        void onDotsChanged(Dots dots);
    }

    private List<Dot> allDot = new ArrayList<>();
    private OnDotsChangedListener listener;

    public List<Dot> getAllDot() {
        return allDot;
    }

    public void setListener(OnDotsChangedListener listener) {
        this.listener = listener;
    }

    public void addDot(Dot dot) {
        this.allDot.add(dot);
        this.listener.onDotsChanged(this);
    }

    public Dot getDot(int index) {
        return allDot.get(index);
    }

    public void setDot(int index, Dot dot) {
        allDot.set(index, dot);
        listener.onDotsChanged(this);
    }

    public void clearAll() {
        allDot.clear();
        this.listener.onDotsChanged(this);
    }

    public void removeBy(int dotPosition) {
        allDot.remove(dotPosition);
        this.listener.onDotsChanged(this);
    }

    public void undo() {
        if (allDot != null && allDot.size() > 0) {
            allDot.remove(allDot.size() - 1);
            this.listener.onDotsChanged(this);
        }
    }

    public int findDot(int x, int y) {
        for (int i = 0; i < allDot.size(); i++) {
            int centerX = allDot.get(i).getCenterX();
            int centerY = allDot.get(i).getCenterY();
            double distance = getDistance(centerX, centerY, x ,y);

            if (distance <= allDot.get(i).getRadius()) {
                return i;
            }
        }
        return -1;
    }

    private double getDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
