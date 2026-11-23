class ProductOfNumbers {
    List<Integer> l = new ArrayList<>();

    public ProductOfNumbers() {
        l.add(1);
    }
    
    public void add(int num) {
        if (num == 0) {
            l = new ArrayList<>();
            l.add(1);
        } else {
            l.add(l.get(l.size() - 1) * num);
        }
    }
    
    public int getProduct(int k) {
        int n = l.size();
        return k < n ? l.get(n - 1) / l.get(n - 1 - k) : 0;
    }
}
