
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * A class that provides compression/decompression functions using Huffman
 * encoding
 *
 * @author Lucas Robertson
 * @version Program 6
 */
public class Huffman {

    private Node root;
    private BidirectionalMap<Character, String> dictionary;

    private class BidirectionalMap<K, V> {

        private HashMap<K, V> keyValue;
        private HashMap<V, K> valueKey;

        public BidirectionalMap() {
            keyValue = new HashMap<>();
            valueKey = new HashMap<>();
        }

        public void put(K key, V value) {
            keyValue.put(key, value);
            valueKey.put(value, key);
        }

        public V getValue(K key) {
            return keyValue.get(key);
        }

        public K getKey(V value) {
            return valueKey.get(value);
        }

        public boolean containsValue(V value) {
            return valueKey.containsKey(value);
        }

        public boolean containsKey(K key) {
            return keyValue.containsKey(key);
        }
    }

    private class Node implements Comparable<Node> {

        private final char character;
        private final int frequency;
        private final Node left, right;

        public Node(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
        }

//        public Node(Node left, Node right) {
//            this.frequency = left.frequency + right.frequency;
//            this.left = left;
//            this.right = right;
//            this.character = Character.MIN_VALUE;
//        }
        public Node(Node left, Node right) {
            this.frequency = left.frequency + right.frequency;
            this.left = left;
            this.right = right;
            if ((int) left.character < (int) right.character) {
                this.character = left.character;
            } else {
                this.character = right.character;
            }
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

//        private int minChar(Node node, int minValue) {
//            if (node.left != null) {
//                minValue = minChar(node.left, minValue);
//            }
//
//            if (node.right != null) {
//                minValue = minChar(node.right, minValue);
//            }
//
//            if (this.character != Character.MIN_VALUE && (int) character < minValue) {
//                minValue = (int) character;
//            }
//
//            return minValue;
//
//        }
        @Override
        public int compareTo(Node o) {
            if (this.frequency > o.frequency) {
                return 1;
            } else if (this.frequency < o.frequency) {
                return -1;
            } //            } else if (this.minChar(this, Character.MAX_VALUE) > o.minChar(o, Character.MAX_VALUE)) {
            //                return 1;
            //            } else if (this.minChar(this, Character.MAX_VALUE) > o.minChar(o, Character.MAX_VALUE)) {
            //                return -1;
            //            } 
            else if (this.character > o.character) {
                return 1;
            } else if (this.character < o.character) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Creates and initializes a Huffman object with the frequencies from the
     * specified file
     *
     * @param fileName the name of the file from which frequencies will be
     * determined
     * @throws FileNotFoundException if the specified file does not exist
     * @throws IOException if an I/O error occurs
     */
    public Huffman(String fileName) throws FileNotFoundException, IOException {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        dictionary = new BidirectionalMap<>();

        HashMap<Character, Integer> frequencies = new HashMap<>(100);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.ready()) {
                char c = (char) reader.read();
                if (frequencies.containsKey(c)) {
                    frequencies.put(c, frequencies.get(c) + 1);
                } else {
                    frequencies.put(c, 1);
                }
            }
        }

        frequencies.put((char) 4, 1);

        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            queue.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (queue.size() > 1) {
            queue.add(new Node(queue.poll(), queue.poll()));
        }

        root = queue.poll();

        buildDictionary(root, "");
    }

    private void buildDictionary(Node node, String code) {
        if (node.left != null) {
            buildDictionary(node.left, code + "0");
        }

        if (node.right != null) {
            buildDictionary(node.right, code + "1");
        }

        //if (node.character != Character.MIN_VALUE) {
        if (node.isLeaf()) {
            dictionary.put(node.character, code);
        }
    }

    /**
     * Compresses the text in the specified input file using the codes generated
     * at construction
     *
     * @param inFileName the name of the file to compress
     * @param outFileName the name of the file to contain the compressed text
     * @throws IOException if an I/O error occurs
     * @throws FileNotFoundException if the specified input file does not exist
     */
    public void compress(String inFileName, String outFileName) throws IOException, FileNotFoundException {
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(outFileName));
        BufferedReader reader = new BufferedReader(new FileReader(inFileName));

        StringBuilder sb = new StringBuilder();
        byte cByte;
        while (true) {
            try {
                sb.append(dictionary.getValue((char) reader.read()));

                if (sb.length() >= 8) {
                    stream.write(Byte.valueOf(sb.substring(0, 8), 2));
                    sb.delete(0, 8);
                }
            } catch (NoSuchElementException ex) {
                break;
            }
        }

        sb.append(dictionary.getValue((char) 4));
        if (sb.length() >= 8) {
            stream.write(Byte.valueOf(sb.substring(0, 8), 2));
            sb.delete(0, 8);
        }
        stream.write(Byte.valueOf(sb.toString(), 2));

        stream.close();
        reader.close();

    }

    /**
     * Decompresses the codes in the specified file using the codes generated at
     * construction
     *
     * @param inFileName the name of the file to decompress
     * @param outFileName the name of the file to contain the decompressed text
     * @throws IOException if an I/O error occurs
     * @throws FileNotFoundException if the specified input file does not exist
     */
    public void decompress(String inFileName, String outFileName) throws IOException, FileNotFoundException {
        BufferedWriter writer;
        try (BufferedReader reader = new BufferedReader(new FileReader(inFileName))) {
            writer = new BufferedWriter(new FileWriter(outFileName));
            String current = "";
            while (reader.ready()) {
                current += (char) reader.read();
                if (dictionary.containsValue(current)) {
                    writer.write(dictionary.getKey(current));
                    current = "";
                }
            }
        }
        writer.close();
    }

    @Override
    public String toString() {
        return "|" + preOrder(root, "") + "|";
    }

    private String preOrder(Node node, String string) {

        //if (node.character != Character.MIN_VALUE) {
        if (node.isLeaf()) {
            string += node.character;
        }

        if (node.left != null) {
            string = preOrder(node.left, string);
        }

        if (node.right != null) {
            string = preOrder(node.right, string);
        }

        return string;
    }
}
