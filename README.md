# algorithmHW
2019-2 알고리즘 분석(CSI3108-01) HW 모음
접근법이 고정된 것은 설명을 달지 않았음.

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
- Cooley-Tukey FFT(fast fourier transform) 알고리즘 이용하여 주어지는 다항식에 대해 DFT(discrete fourier transform)를 계산하는 자바 프로그램을 작성

## HW4
- n개의 점을 가지고 하나의 연결 성분(connected component)으로 된 그래프 G=(V, E)에 대해 D.Karger의 Randomized Min-Cut 알고리즘 이용하여 minimum cut을 찾는 자바 프로그램을 작성 (Union-by-Rank, Path Compression 사용)

## HW5
- Symmetric Traveling Salesman 문제를 해결하는 DP로 최적 싸이클을 찾는 자바 프로그램을 작성

## HW6
- Linear Programming 문제를 해결하기 위한 Simplex 알고리즘을 자바 프로그램으로 구현
