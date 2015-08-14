function [] = plotQuotRes()
	for i = 1:100:1901
		data = csvread(strcat('quotResults', num2str(i), '.csv'));
		plot(data(:,2:end))
		title('Partición usando cuociente')
		xlabel('Tamaño de la matriz')
		ylabel('Tiempo de ejecución [ms]')
		legend('1 Thread', '4 Threads', '8 Threads', '16 Threads', '32 Threads')
		grid minor
		print(strcat('quotRes', num2str(i)), '-dsvg')
	end
end
