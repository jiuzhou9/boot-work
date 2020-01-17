set terminal png transparent size 640,240
set size 1.0,1.0

set terminal png transparent size 640,480
set output 'commits_by_author.png'
set key left top
set yrange [0:]
set xdata time
set timefmt "%s"
set format x "%Y-%m-%d"
set grid y
set ylabel "Commits"
set xtics rotate
set bmargin 6
plot 'commits_by_author.dat' using 1:2 title "Wang Jiuzhou" w lines, 'commits_by_author.dat' using 1:3 title "zipei" w lines, 'commits_by_author.dat' using 1:4 title "liushuijing" w lines, 'commits_by_author.dat' using 1:5 title "wangkaiyu" w lines, 'commits_by_author.dat' using 1:6 title "Wang KaiYu" w lines, 'commits_by_author.dat' using 1:7 title "yingling" w lines, 'commits_by_author.dat' using 1:8 title "wangzipei" w lines, 'commits_by_author.dat' using 1:9 title "wangjiuzhou" w lines, 'commits_by_author.dat' using 1:10 title "Wang Kaiyu" w lines, 'commits_by_author.dat' using 1:11 title "chaoyingling" w lines, 'commits_by_author.dat' using 1:12 title "xiechaohong" w lines
