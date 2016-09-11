#pragma once
#include <string>

enum TriangleType
{
	NORMAL,
	ISOSCELES,
	EQUILATERAL,
	NOT_TRIANGLE
};

class CDetermineTriangle
{
public:
	CDetermineTriangle(std::string a, std::string b, std::string c);
	void DetermineTriangleType();
	void OutputResult();

private:
	double m_a;
	double m_b;
	double m_c;
	TriangleType m_type;
};
