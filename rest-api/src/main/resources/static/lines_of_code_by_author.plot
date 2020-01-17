set terminal png transparent size 640,240
set size 1.0,1.0

set terminal png transparent size 640,480
set output 'lines_of_code_by_author.png'
set key left top
set yrange [0:]
set xdata time
set timefmt "%s"
set format x "%Y-%m-%d"
set grid y
set ylabel "Lines"
set xtics rotate
set bmargin 6
plot 'lines_of_code_by_author.dat' using 1:2 title "Wang Jiuzhou" w lines, 'lines_of_code_by_author.dat' using 1:3 title "zipei" w lines, 'lines_of_code_by_author.dat' using 1:4 title "liushuijing" w lines, 'lines_of_code_by_author.dat' using 1:5 title "wangkaiyu" w lines, 'lines_of_code_by_author.dat' using 1:6 title "Wang KaiYu" w lines, 'lines_of_code_by_author.dat' using 1:7 title "yingling" w lines, 'lines_of_code_by_author.dat' using 1:8 title "wangzipei" w lines, 'lines_of_code_by_author.dat' using 1:9 title "wangjiuzhou" w lines, 'lines_of_code_by_author.dat' using 1:10 title "Wang Kaiyu" w lines, 'lines_of_code_by_author.dat' using 1:11 title "chaoyingling" w lines, 'lines_of_code_by_author.dat' using 1:12 title "xiechaohong" w lines
