# algorithmHW
- 2019-2 알고리즘 분석(CSI3108-01) HW 모음.

## HW1
- 문제: NxNxN개의 정육면체가 있을 때 위와 옆의 두 면으로부터 구멍을 뚫으면, 총 몇 개의 정육면체가 남게 되는지를 계산하는 자바 프로그램을 작성
- 접근법: 구멍의 dimension에 따라 제거되는 좌표를 구함. 제거되는 좌표들을 중복 카운트하지 않기 위해 `HashSet<ArrayList<Integer>>`을 사용함.

## HW2
- 문제: 2차원 평면에 n개의 점들이 주어질 때, 서로 다른 3개 이상의 점들을 지나는 선분들을 찾고, 이 선분들이 서로 교차하여 만들어지는 점들의 수를 계산하는 자바 프로그램을 작성
- 접근법
  1. 세 점으로 가능한 조합을 모두 구함.
  2. ccw(counterclockwise)를 이용해 선분이 될 수 있는지 검사.
  3. 선분 상에 있는 점 추가.
  4. 선분의 시작점과 끝 점을 구함.
  5. ccw를 이용해 교차하는 선분들을 구함.
  6. 기울기와 y절편을 이용해 교점 구함.

## HW3
- 문제: Cooley-Tukey FFT(fast fourier transform) 알고리즘 이용하여 주어지는 다항식에 대해 DFT(discrete fourier transform)를 계산하는 자바 프로그램을 작성
- 접근법
  1. 주어진 다항식의 coefficient를 `2**n`만큼의 길이를 가지는 array로 만들어 줌. (없는 경우 0으로 처리)
  2. coefficient array를 even(0, 2, 4...)과 odd(1, 3, 5...)로 반씩 나눔.
  3. array의 길이가 1일 때를 base case로 만들어 재귀적으로 1 ~ 2 적용. 
  4. complex array에 base case 부터 순차적으로 `k`, `k + n/2`에 각각 복소수 곱과 복소수 합 값을 넣어줌. (`k`와 `k + n/2`가 plus minus pair인 것을 이용)

## HW4
- n개의 점을 가지고 하나의 연결 성분(connected component)으로 된 그래프 G=(V, E)에 대해 D.Karger의 Randomized Min-Cut 알고리즘 이용하여 minimum cut을 찾는 자바 프로그램을 작성 (Union-by-Rank, Path Compression 사용)
- 접근법
  1. graph 초기화. (edge 추가)
  2. parents 초기화.
  3. edge들에 랜덤하게 weight 할당.
  4. weight 오름차순으로 edge 정렬.
  5. 서로 다른 parent를 가지는 vertex가 2개 남을 때까지 find와 union을 통해 vertex 합침. (mst 구하는 과정)
  6. 서로 다른 parent를 가지는 vertex 개수, 즉 cut을 구함.
  7. 3 ~ 6 과정을 n*(n-1)/2 반복. (해당 값은 마지막에 min cut을 찾았을 때를 가정한 확률)

## HW5
- Symmetric Traveling Salesman 문제를 해결하는 DP로 최적 싸이클을 찾는 자바 프로그램을 작성

## HW6
- Linear Programming 문제를 해결하기 위한 Simplex 알고리즘을 자바 프로그램으로 구현
