#include "stdafx.h"
#include <string>
#include <iostream>
#include "CDetermineTriangle.h"
#include <boost\lexical_cast.hpp>

CDetermineTriangle::CDetermineTriangle(const std::string& a, const std::string& b, const std::string& c)
{
	try
	{
		m_a = boost::lexical_cast<double>(a);
		m_b = boost::lexical_cast<double>(b);
		m_c = boost::lexical_cast<double>(c);
	}
	catch (const std::exception&)
	{
		//throw std::exception("Invalid arguments. Parameters must have type <double>");
		throw std::exception("Неверный аргумент.\n Аргументами должны быть числа.");
	}

	if (m_a <= 0 || m_b <= 0 || m_c <= 0)
	{
		//throw std::exception("Invalid arguments. Side must be more than 0");
		throw std::exception("Неверный аргумент.\nСтороны должны иметь длину больше 0");
	}
}

void CDetermineTriangle::DetermineTriangleType()
{
	if ((m_a >= m_b + m_c) || (m_b >= m_a + m_c) || (m_c >= m_a + m_b))
	{
		m_type = NOT_TRIANGLE;
	}
	else if ((m_a == m_b) && (m_b == m_c) && (m_a == m_c))
	{
		m_type = EQUILATERAL;
	}
	else if ((m_a == m_b) || (m_a == m_c) || (m_b == m_c))
	{
		m_type = ISOSCELES;
	}
	else
	{
		m_type = NORMAL;
	}
}

void CDetermineTriangle::OutputResult()
{
	switch (m_type)
	{
	case NORMAL:
		std::cout << "Обычный" << std::endl;
		break;
	case ISOSCELES:
		std::cout << "Равнобедренный" << std::endl;
		break;
	case EQUILATERAL:
		std::cout << "Равносторонний" << std::endl;
		break;
	case NOT_TRIANGLE:
		std::cout << "Не треугольник" << std::endl;
		break;
	default:
		break;
	}
}
