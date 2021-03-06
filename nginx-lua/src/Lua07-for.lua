---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by shicongyang.
--- DateTime: 2019-06-12 18:19
--- for

--for var = begin, finish, step do
--    --body
--end

--关于数字 for 需要关注以下几点： 1.var 从 begin 变化到 finish，每次变化都以 step 作为步长递增 var 2.begin、finish、step 三个表达式只会在循环开始时执行一次 3.第三个表达式 step 是可选的，默认为 1 4.控制变量 var 的作用域仅在 for 循环内，需要在外面控制，则需将值赋给一个新的变量 5.循环过程中不要改变控制变量的值，那样会带来不可预知的影响

for i = 1, 5 do
    print(i)
end

for i = 10, 1, -1 do
    print(i)
end


-- 打印数组a的所有值
local a = { "a", "b", "c", "d" }
for i, v in ipairs(a) do
    print("index:", i, " value:", v)
end

--标准库提供了几种迭代器，包括用于迭代文件中每行的（io.lines）、 迭代 table 元素的（pairs）、迭代数组元素的（ipairs）、迭代字符串中单词的（string.gmatch）等。