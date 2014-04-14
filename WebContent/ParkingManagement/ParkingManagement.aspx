﻿<%@ Page Language="C#" AutoEventWireup="true"  MasterPageFile="~/Site.Master" CodeBehind="ParkingManagement.aspx.cs" Inherits="ParkingManagement.WebContent.ParkingManagement.ParkingManagement" %>

      


<asp:Content runat="server" ID="FeaturedContent" ContentPlaceHolderID="FeaturedContent">
     <section class="featured">
         <div class="content-wrapper">
           <h1>Parking Management</h1>
             <asp:Panel ID="Panel2" runat="server" Height="909px">
                 <asp:Button ID="Button1" runat="server" Text="Edit Lot" BackColor="Black" BorderColor="Black" ForeColor="White" Width="48%" OnClick="Button1_Click" />
                 <asp:Button ID="Button6" runat="server" BackColor="Black" BorderColor="Black" ForeColor="White" OnClick="Button6_Click" Text="Add Lot" Width="49%" />
                 <asp:MultiView ID="MultiViewManage" runat="server">
                     <asp:View ID="View1" runat="server">
                         <asp:DropDownList ID="DropDownList1" runat="server" OnSelectedIndexChanged="DropDownList1_SelectedIndexChanged" Width="120px">
                             <asp:ListItem Selected="True">Select Lot</asp:ListItem>
                             <asp:ListItem>Lot A</asp:ListItem>
                             <asp:ListItem>Lot B</asp:ListItem>
                             <asp:ListItem>Lot C</asp:ListItem>
                         </asp:DropDownList>
                         <asp:DropDownList ID="DropDownList2" runat="server" Width="100px">
                             <asp:ListItem>Faculty</asp:ListItem>
                             <asp:ListItem>Student</asp:ListItem>
                             <asp:ListItem>Guest</asp:ListItem>
                         </asp:DropDownList>
                         <asp:DropDownList ID="DropDownList3" runat="server" Width="100px">
                             <asp:ListItem>Open</asp:ListItem>
                             <asp:ListItem>Closed</asp:ListItem>
                         </asp:DropDownList>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:Label ID="Label5" runat="server" Font-Size="Small" Text="Columns"></asp:Label>
                         <asp:TextBox ID="TextBox1" runat="server" Height="16px" Width="50px"></asp:TextBox>
                         &nbsp;&nbsp;&nbsp;
                         <asp:Label ID="Label6" runat="server" Font-Size="Small" Text="Rows"></asp:Label>
                         <asp:TextBox ID="TextBox2" runat="server" Width="50px"></asp:TextBox>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:Button ID="Button7" runat="server" BackColor="Black" ForeColor="White" OnClick="Button7_Click" Text="Save" Width="100px" />
                         &nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:Button ID="Button8" runat="server" BackColor="Black" ForeColor="White" OnClick="Button8_Click" Text="Undo" Width="100px" />
                         <br />
                         <br />
                         <asp:Label ID="Label7" runat="server" Font-Size="Medium" Text="Edit Parking Space:"></asp:Label>
                         <br />
                         <asp:DropDownList ID="DropDownList4" runat="server" Width="100px">
                             <asp:ListItem Value="0">Column</asp:ListItem>
                         </asp:DropDownList>
                         <asp:DropDownList ID="DropDownList5" runat="server" Width="100px">
                             <asp:ListItem Value="0">Row</asp:ListItem>
                         </asp:DropDownList>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:DropDownList ID="DropDownList6" runat="server" Width="100px">
                             <asp:ListItem Value="0">Status</asp:ListItem>
                             <asp:ListItem>Available</asp:ListItem>
                             <asp:ListItem>Reserved</asp:ListItem>
                             <asp:ListItem>In Use</asp:ListItem>
                         </asp:DropDownList>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:Label ID="Label8" runat="server" Font-Size="Small" Text="User:"></asp:Label>
                         &nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:TextBox ID="TextBox3" runat="server" Width="188px"></asp:TextBox>
                         &nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:Button ID="Button9" runat="server" BackColor="Black" ForeColor="White" OnClick="Button9_Click" Text="Save" Width="100px" />
                         &nbsp;&nbsp;&nbsp;
                         <asp:Button ID="Button10" runat="server" BackColor="Black" ForeColor="White" OnClick="Button10_Click" Text="Undo" Width="100px" />
                     </asp:View>
                     <asp:View ID="View2" runat="server">
                         <p>
                             <br />
                             Use a different map using directions at <a href="https://developers.google.com/maps/documentation/javascript/shapes#polygons">https://developers.google.com/maps/documentation/javascript/shapes#polygons</a>&nbsp; as this includes all the drawing requirements.<br />
                             <br />
                             <br />
                         </p>
                         <asp:Panel ID="Panel1" runat="server" Height="460px" Width="800px">
                             <div id="map-canvas"/>
                             <asp:Label ID="Label4" runat="server" Height="450px" Width="5px"></asp:Label>
                             <br />
                             <br />
                             <br />
                             <!--
                     <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d13139.34871349347!2d-84.46062709999998!3d39.03338469999997!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x8841b06e0b8b16e9%3A0x3ee280bce0f69454!2sNorthern+Kentucky+University!5e1!3m2!1sen!2sus!4v1395693200756" width="600" height="450" frameborder="0" style="border:0"></iframe>
                     -->
                             <br />
                         </asp:Panel>
                         <asp:Panel ID="Panel3" runat="server">
                             <br />
                             <br />
                             <asp:Panel ID="Panel4" runat="server" BorderColor="#666666" BorderStyle="Solid">
                                 &nbsp;<asp:Label ID="Label11" runat="server" Font-Size="Small" Text="Name: "></asp:Label>
                                 &nbsp;&nbsp;
                                 <asp:TextBox ID="TextBox4" runat="server" Width="130px"></asp:TextBox>
                                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<asp:Label ID="Label9" runat="server" Font-Size="Small" Text="Rows:"></asp:Label>
                                 &nbsp;
                                 <asp:TextBox ID="TextBox5" runat="server" Width="75px"></asp:TextBox>
                                 &nbsp;&nbsp;
                                 <asp:Label ID="Label10" runat="server" Font-Size="Small" Text="Columns:"></asp:Label>
                                 &nbsp;
                                 <asp:TextBox ID="TextBox6" runat="server" Width="75px"></asp:TextBox>
                                 &nbsp;&nbsp;&nbsp;&nbsp;
                                 <asp:DropDownList ID="DropDownList7" runat="server" Height="21px" Width="110px">
                                     <asp:ListItem Value="0">Designation</asp:ListItem>
                                     <asp:ListItem>Faculty</asp:ListItem>
                                     <asp:ListItem>Guest</asp:ListItem>
                                     <asp:ListItem>Student</asp:ListItem>
                                 </asp:DropDownList>
                                 &nbsp;&nbsp;&nbsp;&nbsp;
                                 <asp:DropDownList ID="DropDownList8" runat="server" Width="110px">
                                     <asp:ListItem Value="0">Status</asp:ListItem>
                                     <asp:ListItem>Open</asp:ListItem>
                                     <asp:ListItem>Closed</asp:ListItem>
                                 </asp:DropDownList>
                             </asp:Panel>
                             <br />
                             <asp:Panel ID="Panel5" runat="server" Height="345px">
                                 &nbsp;<asp:Label ID="Label12" runat="server" Font-Size="Small" Text="Latitude:" Width="198px"></asp:Label>
                                 <asp:Label ID="Label13" runat="server" Font-Size="Small" Text="Longitude:" Width="205px"></asp:Label>
                                 <asp:Label ID="Label14" runat="server" Font-Size="Small" Text="Latitude:" Width="195px"></asp:Label>
                                 <asp:Label ID="Label15" runat="server" Font-Size="Small" Text="Longitude:" Width="180px"></asp:Label>
                                 <br />
                                 <asp:Panel ID="Panel6" runat="server" BorderColor="#CCCCCC" BorderStyle="Solid" BorderWidth="2px" CssClass="float-left" Height="165px" Width="395px">
                                     <asp:TextBox ID="TextBox7" runat="server" Width="180px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox8" runat="server" Width="180px"></asp:TextBox>
                                     <br />
                                     <asp:TextBox ID="TextBox9" runat="server" Width="180px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox10" runat="server" Width="180px"></asp:TextBox>
                                     <br />
                                     <asp:TextBox ID="TextBox11" runat="server" Width="180px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox12" runat="server" Width="180px"></asp:TextBox>
                                     <br />
                                     <asp:TextBox ID="TextBox13" runat="server" Width="180px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox14" runat="server" Width="180px"></asp:TextBox>
                                 </asp:Panel>
                                 <asp:Panel ID="Panel8" runat="server" BorderColor="#CCCCCC" BorderStyle="Solid" BorderWidth="2px" CssClass="float-left" Height="165px" Width="395px">
                                     &nbsp;<asp:TextBox ID="TextBox15" runat="server" Width="180px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox16" runat="server" Width="180px"></asp:TextBox>
                                     <br />
                                     <asp:TextBox ID="TextBox17" runat="server" Width="180px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox18" runat="server" Width="180px"></asp:TextBox>
                                     <br />
                                     <asp:TextBox ID="TextBox19" runat="server" Width="180px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox20" runat="server" Width="180px"></asp:TextBox>
                                     <br />
                                     <asp:TextBox ID="TextBox21" runat="server" Width="180px"></asp:TextBox>
                                     &nbsp;<asp:TextBox ID="TextBox22" runat="server" Width="180px"></asp:TextBox>
                                 </asp:Panel>
                                 <br />
                                 <asp:Button ID="Button11" runat="server" BackColor="Black" CssClass="float-left" Font-Size="Medium" ForeColor="White" Text="Submit" Width="49%" />
                                 <asp:Button ID="Button12" runat="server" BackColor="Black" CssClass="float-right" Font-Size="Medium" ForeColor="White" Text="Clear" Width="49%" />
                                 <br />
                                 <br />
                                 <br />
                                 <br />
                             </asp:Panel>
                             <br />
                             <br />
                             <br />
                         </asp:Panel>
                         <p>
                             <!--           <iframe width="600"  height="450"  frameborder="0" style="border:0"  src="https://www.google.com/maps/embed/v1/place?key=AIzaSyCLrr7e84uD7p0aV3fXjSY6mjG7tePIIbk&q=Space+Needle,Seattle+WA"></iframe>    -->&nbsp;</p>
                     </asp:View>
                 </asp:MultiView>
             </asp:Panel>
        </div>
    </section>
</asp:Content>