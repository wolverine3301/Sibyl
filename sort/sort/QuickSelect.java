package sort;

public interface QuickSelect {
    /**
     * Given k in [0, n-1], returns an array value from arr such that k array
     * values are less than or equal to the one returned. The input array will
     * be rearranged to have this value in location arr[k], with all smaller
     * elements moved to arr[0, k-1] (in arbitrary order) and all larger elements
     * in arr[k+1, n-1] (also in arbitrary order).
     */
    static int select(int[] arr, int k) {
        int n = arr.length;
        int l = 0;
        int ir = n - 1;

        int a;
        int i, j, mid;
        for (;;) {
            if (ir <= l + 1) {
                if (ir == l + 1 && arr[ir] < arr[l]) {
                    Sort.swap(arr, l, ir);
                }
                return arr[k];
            } else {
                mid = (l + ir) >> 1;
                Sort.swap(arr, mid, l + 1);
                if (arr[l] > arr[ir]) {
                    Sort.swap(arr, l, ir);
                }
                if (arr[l + 1] > arr[ir]) {
                    Sort.swap(arr, l + 1, ir);
                }
                if (arr[l] > arr[l + 1]) {
                    Sort.swap(arr, l, l + 1);
                }
                i = l + 1;
                j = ir;
                a = arr[l + 1];
                for (;;) {
                    do {
                        i++;
                    } while (arr[i] < a);
                    do {
                        j--;
                    } while (arr[j] > a);
                    if (j < i) {
                        break;
                    }
                    Sort.swap(arr, i, j);
                }
                arr[l + 1] = arr[j];
                arr[j] = a;
                if (j >= k) {
                    ir = j - 1;
                }
                if (j <= k) {
                    l = i;
                }
            }
        }
    }

    /**
     * Given k in [0, n-1], returns an array value from arr such that k array
     * values are less than or equal to the one returned. The input array will
     * be rearranged to have this value in location arr[k], with all smaller
     * elements moved to arr[0, k-1] (in arbitrary order) and all larger elements
     * in arr[k+1, n-1] (also in arbitrary order).
     */
    static float select(float[] arr, int k) {
        int n = arr.length;
        int l = 0;
        int ir = n - 1;

        float a;
        int i, j, mid;
        for (;;) {
            if (ir <= l + 1) {
                if (ir == l + 1 && arr[ir] < arr[l]) {
                    Sort.swap(arr, l, ir);
                }
                return arr[k];
            } else {
                mid = (l + ir) >> 1;
                Sort.swap(arr, mid, l + 1);
                if (arr[l] > arr[ir]) {
                    Sort.swap(arr, l, ir);
                }
                if (arr[l + 1] > arr[ir]) {
                    Sort.swap(arr, l + 1, ir);
                }
                if (arr[l] > arr[l + 1]) {
                    Sort.swap(arr, l, l + 1);
                }
                i = l + 1;
                j = ir;
                a = arr[l + 1];
                for (;;) {
                    do {
                        i++;
                    } while (arr[i] < a);
                    do {
                        j--;
                    } while (arr[j] > a);
                    if (j < i) {
                        break;
                    }
                    Sort.swap(arr, i, j);
                }
                arr[l + 1] = arr[j];
                arr[j] = a;
                if (j >= k) {
                    ir = j - 1;
                }
                if (j <= k) {
                    l = i;
                }
            }
        }
    }

    /**
     * Given k in [0, n-1], returns an array value from arr such that k array
     * values are less than or equal to the one returned. The input array will
     * be rearranged to have this value in location arr[k], with all smaller
     * elements moved to arr[0, k-1] (in arbitrary order) and all larger elements
     * in arr[k+1, n-1] (also in arbitrary order).
     */
    static double select(double[] arr, int k) {
        int n = arr.length;
        int l = 0;
        int ir = n - 1;

        double a;
        int i, j, mid;
        for (;;) {
            if (ir <= l + 1) {
                if (ir == l + 1 && arr[ir] < arr[l]) {
                    Sort.swap(arr, l, ir);
                }
                return arr[k];
            } else {
                mid = (l + ir) >> 1;
                Sort.swap(arr, mid, l + 1);
                if (arr[l] > arr[ir]) {
                    Sort.swap(arr, l, ir);
                }
                if (arr[l + 1] > arr[ir]) {
                    Sort.swap(arr, l + 1, ir);
                }
                if (arr[l] > arr[l + 1]) {
                    Sort.swap(arr, l, l + 1);
                }
                i = l + 1;
                j = ir;
                a = arr[l + 1];
                for (;;) {
                    do {
                        i++;
                    } while (arr[i] < a);
                    do {
                        j--;
                    } while (arr[j] > a);
                    if (j < i) {
                        break;
                    }
                    Sort.swap(arr, i, j);
                }
                arr[l + 1] = arr[j];
                arr[j] = a;
                if (j >= k) {
                    ir = j - 1;
                }
                if (j <= k) {
                    l = i;
                }
            }
        }
    }

    /**
     * Given k in [0, n-1], returns an array value from arr such that k array
     * values are less than or equal to the one returned. The input array will
     * be rearranged to have this value in location arr[k], with all smaller
     * elements moved to arr[0, k-1] (in arbitrary order) and all larger elements
     * in arr[k+1, n-1] (also in arbitrary order).
     */
    static <T extends Comparable<? super T>> T select(T[] arr, int k) {
        int n = arr.length;
        int l = 0;
        int ir = n - 1;

        T a;
        int i, j, mid;
        for (;;) {
            if (ir <= l + 1) {
                if (ir == l + 1 && arr[ir].compareTo(arr[l]) < 0) {
                    Sort.swap(arr, l, ir);
                }
                return arr[k];
            } else {
                mid = (l + ir) >> 1;
                Sort.swap(arr, mid, l + 1);
                if (arr[l].compareTo(arr[ir]) > 0) {
                    Sort.swap(arr, l, ir);
                }
                if (arr[l + 1].compareTo(arr[ir]) > 0) {
                    Sort.swap(arr, l + 1, ir);
                }
                if (arr[l].compareTo(arr[l + 1]) > 0) {
                    Sort.swap(arr, l, l + 1);
                }
                i = l + 1;
                j = ir;
                a = arr[l + 1];
                for (;;) {
                    do {
                        i++;
                    } while (arr[i].compareTo(a) < 0);
                    do {
                        j--;
                    } while (arr[j].compareTo(a) > 0);
                    if (j < i) {
                        break;
                    }
                    Sort.swap(arr, i, j);
                }
                arr[l + 1] = arr[j];
                arr[j] = a;
                if (j >= k) {
                    ir = j - 1;
                }
                if (j <= k) {
                    l = i;
                }
            }
        }
    }

    /**
     * Find the median of an array of type integer.
     */
    static int median(int[] a) {
        int k = a.length / 2;
        return select(a, k);
    }

    /**
     * Find the median of an array of type float.
     */
    static float median(float[] a) {
        int k = a.length / 2;
        return select(a, k);
    }

    /**
     * Find the median of an array of type double.
     */
    static double median(double[] a) {
        int k = a.length / 2;
        return select(a, k);
    }

    /**
     * Find the median of an array of type double.
     */
    static <T extends Comparable<? super T>> T median(T[] a) {
        int k = a.length / 2;
        return select(a, k);
    }

    /**
     * Find the first quantile (p = 1/4) of an array of type integer.
     */
    static int q1(int[] a) {
        int k = a.length / 4;
        return select(a, k);
    }

    /**
     * Find the first quantile (p = 1/4) of an array of type float.
     */
    static float q1(float[] a) {
        int k = a.length / 4;
        return select(a, k);
    }

    /**
     * Find the first quantile (p = 1/4) of an array of type double.
     */
    static double q1(double[] a) {
        int k = a.length / 4;
        return select(a, k);
    }

    /**
     * Find the first quantile (p = 1/4) of an array of type double.
     */
    static <T extends Comparable<? super T>> T q1(T[] a) {
        int k = a.length / 4;
        return select(a, k);
    }

    /**
     * Find the third quantile (p = 3/4) of an array of type integer.
     */
    static int q3(int[] a) {
        int k = 3 * a.length / 4;
        return select(a, k);
    }

    /**
     * Find the third quantile (p = 3/4) of an array of type float.
     */
    static float q3(float[] a) {
        int k = 3 * a.length / 4;
        return select(a, k);
    }

    /**
     * Find the third quantile (p = 3/4) of an array of type double.
     */
    static double q3(double[] a) {
        int k = 3 * a.length / 4;
        return select(a, k);
    }

    /**
     * Find the third quantile (p = 3/4) of an array of type double.
     */
    static <T extends Comparable<? super T>> T q3(T[] a) {
        int k = 3 * a.length / 4;
        return select(a, k);
    }
}
