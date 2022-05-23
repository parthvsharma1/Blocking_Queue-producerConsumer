public class node {
    private int data;
    public node nextNode;
    public node()
    {
        this.data=0;
        this.nextNode=null;
    }
    public node(int data)
    {
        this.data=data;
        this.nextNode=null;
    }
    public int getData(){
        return data;
    }
}
