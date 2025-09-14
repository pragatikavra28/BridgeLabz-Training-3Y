import java.util.Scanner;

public class AreaTriangle {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter base: ");
        double base = input.nextDouble();
        System.out.print("Enter height: ");
        double height = input.nextDouble();

        double areaCm = 0.5 * base * height;
        double areaInches = areaCm / 6.4516; // 1 sq inch = 6.4516 sq cm

        System.out.println("The area of triangle in square cm is " + areaCm + " and in square inches is " + areaInches);
    }
}
