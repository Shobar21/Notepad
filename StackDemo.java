class StackDemo {
    Stack Undo = new Stack();
    Stack redo = new Stack();
    StackDemo(String text) {
        String word[] = text.split("\\s");
            for(String elements : word) {
                Undo.push(elements);
            } 
    }
    //undo
    public String undo() {
        Stack s3 = new Stack();
        redo.push((Undo.pop()));
        String store ="";
        Node cur = Undo.top;
        while(cur != null) {
            s3.push(Undo.pop());
            cur = cur.next;
        }
        Node temp = s3.top;
        while(temp != null) {
            store = store + temp.key +" ";
            temp = temp.next;
        }
        return store;
    }
}
class Node {
    String key;
    Node next,prev;
    // int var;
    // boolean bool;
    public Node(String key) {
        this.key = key;
    }

}
class Stack {
    Node top;
// Constructor to initialize stack
    Stack()
    {
        this.top = null; 
    }
    public void push(String x)
    {
        Node newNode = new Node(x);
        
        if(top==null){
            top = newNode;
        }
        else{
            newNode.next = top;
            top.prev = newNode;
            top = newNode;
        }
    }
    public String pop() 
    {   
        //System.out.println("\nremove :" + top.key);
        if(top==null)
            return null;

        String retrieve =  top.key;
        top = top.next;
        return retrieve;
    }
} 
