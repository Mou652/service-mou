package cn.moublog.linked;

/**
 * @author: mou
 * @date: 2020/6/9
 */
public abstract class SeqListAdapter implements ISeqList {
    public boolean isEmpty() {
        return false;
    }

    public int length() {
        return 0;
    }

    public Object get(int index) {


        return null;
    }

    public Object set(int index, Object data) {
        return null;
    }

    public boolean add(int index, Object data) {
        return false;
    }

    public boolean add(Object data) {
        return false;
    }

    public Object remove(int index) {
        return null;
    }

    public boolean remove(Object data) {
        return false;
    }

    public boolean removeAll(Object data) {
        return false;
    }

    public void clear() {

    }

    public boolean contains(Object data) {
        return false;
    }

    public int indexOf(Object data) {
        return 0;
    }

    public int lastIndexOf(Object data) {
        return 0;
    }
}
